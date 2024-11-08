package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup.SmallGroupMemberLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LineUpConfirmManager {
    private final UserDownstream userDownstream;
    private final NewFamilyDownstream newFamilyDownstream;
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final SmallGroupMemberLineUpReader smallGroupMemberLineUpReader;
    private final NewFamilyGroupMemberLineUpReader newFamilyGroupMemberLineUpReader;
    private final NewFamilyLineUpReader newFamilyLineUpReader;

    public void confirm(Term term) {
        List<User> allUsers = userDownstream.findAll();
        List<User> activeUsers = allUsers.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());

        // 그루터기
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        // 순장, 순원
        List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps = smallGroupLeaderLineUpReader.findByTermId(term.getId());
        List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps = newFamilyGroupLeaderLineUpReader.findByTermId(term.getId());
        List<SmallGroupMemberLineUp> smallGroupMemberLineUps = smallGroupMemberLineUpReader.findByTermId(term.getId());
        List<NewFamilyGroupMemberLineUp> newFamilyGroupMemberLineUps = newFamilyGroupMemberLineUpReader.findByTermId(term.getId());

        // 새가족
        List<NewFamilyLineUp> newFamilyLineUps = newFamilyLineUpReader.findByTermId(term.getId());
        List<Long> newFamilyIds = newFamilyLineUps.stream().map(NewFamilyLineUp::getNewFamilyId).collect(Collectors.toList());
        List<NewFamily> newFamilies = newFamilyDownstream.findByIdInOrThrow(newFamilyIds);

        boolean areValidNewFamilies = newFamilies.stream()
                .allMatch(newFamily -> newFamilyDownstream.isNewFamilyByUserId(newFamily.getUserId()));

        if (!areValidNewFamilies) {
            throw new IllegalStateException("새가족이 아닌 지체가 포함되어 있습니다. 일반 라인업을 진행해주세요.");
        }

        // 로깅
        log.info("active user: {}", activeUsers.size());
        log.info("senior pastor: {}", stumpLineUp.getSeniorPastorUserId() != null ? 1 : 0);
        log.info("junior pastor: {}", stumpLineUp.getJuniorPastorUserIds().size());
        log.info("cody: {}", stumpLineUp.getCodyUserIds().size());
        log.info("small grp leader: {}", smallGroupLeaderLineUps.size());
        log.info("new fam grp leader: {}", newFamilyGroupLeaderLineUps.size());
        log.info("small grp member: {}", smallGroupMemberLineUps.size());
        log.info("new fam grp member: {}", newFamilyGroupMemberLineUps.size());
        log.info("new fam: {}", newFamilyLineUps.size());

        // DIFF CHECK
        Set<Long> placedUserIdSet = makePlacedUserIdSet(
                stumpLineUp,
                smallGroupLeaderLineUps,
                newFamilyGroupLeaderLineUps,
                smallGroupMemberLineUps,
                newFamilyGroupMemberLineUps,
                newFamilyLineUps,
                newFamilies
        );
        Set<Long> activeUserIdSet = activeUsers.stream().map(User::getId).collect(Collectors.toSet());

        checkDiff(allUsers, activeUserIdSet, placedUserIdSet);
    }

    private Set<Long> makePlacedUserIdSet(
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps,
            List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps,
            List<SmallGroupMemberLineUp> smallGroupMemberLineUps,
            List<NewFamilyGroupMemberLineUp> newFamilyGroupMemberLineUps,
            List<NewFamilyLineUp> newFamilyLineUps,
            List<NewFamily> newFamilies
    ) {
        // 담당 교역자 검증
        if (stumpLineUp.getSeniorPastorUserId() == null) {
            throw new IllegalStateException("담당 교역자를 지정해주세요.");
        }

        // 지정 코디 검증
        Set<Long> assignedCodyUserIds = new HashSet<>();
        assignedCodyUserIds.addAll(smallGroupLeaderLineUps.stream().map(SmallGroupLeaderLineUp::getCodyUserId).collect(Collectors.toSet()));
        assignedCodyUserIds.addAll(newFamilyGroupLeaderLineUps.stream().map(NewFamilyGroupLeaderLineUp::getCodyUserId).collect(Collectors.toSet()));

        if (!new HashSet<>(stumpLineUp.getCodyUserIds()).containsAll(assignedCodyUserIds)) {
            throw new IllegalStateException("그루터기에 지정된 코디에게만 순장을 배정할 수 있습니다.");
        }

        // 지정 일반 순장 검증
        Set<Long> smallGroupLeaderUserIds = smallGroupLeaderLineUps.stream().map(SmallGroupLeaderLineUp::getLeaderUserId).collect(Collectors.toSet());
        Set<Long> assignedSmallGroupLeaderUserIds = smallGroupMemberLineUps.stream().map(
                        smallGroupMemberLineUp ->
                                smallGroupLeaderLineUps.stream()
                                        .filter(smallGroupLeaderLineUp -> smallGroupLeaderLineUp.getId().equals(smallGroupMemberLineUp.getSmallGroupLeaderLineUpId()))
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalStateException("코디 하위에 배정된 일반 순장에게만 일반 순원을 배정할 수 있습니다."))
                                        .getLeaderUserId()
                )
                .collect(Collectors.toSet());

        if (!smallGroupLeaderUserIds.containsAll(assignedSmallGroupLeaderUserIds)) {
            throw new IllegalStateException("코디 하위에 배정된 일반 순장에게만 일반 순원을 배정할 수 있습니다.");
        }

        // 지정 새가족 순장 검증
        Set<Long> newFamilyGroupLeaderUserIds = newFamilyGroupLeaderLineUps.stream().map(NewFamilyGroupLeaderLineUp::getLeaderUserId).collect(Collectors.toSet());
        Set<Long> assignedNewFamilyGroupLeaderUserIds = newFamilyGroupMemberLineUps.stream().map(
                        newFamilyGroupMemberLineUp ->
                                newFamilyGroupLeaderLineUps.stream()
                                        .filter(newFamilyGroupLeaderLineUp -> newFamilyGroupLeaderLineUp.getId().equals(newFamilyGroupMemberLineUp.getNewFamilyGroupLeaderLineUpId()))
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalStateException("코디 하위에 배정된 새가족 순장에게만 새가족 순원을 배정할 수 있습니다."))
                                        .getLeaderUserId()
                )
                .collect(Collectors.toSet());

        assignedNewFamilyGroupLeaderUserIds.addAll(newFamilyLineUps.stream().map(
                        newFamilyLineUp ->
                                newFamilyGroupLeaderLineUps.stream()
                                        .filter(newFamilyGroupLeaderLineUp -> newFamilyGroupLeaderLineUp.getId().equals(newFamilyLineUp.getNewFamilyGroupLeaderLineUpId()))
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalStateException("코디 하위에 배정된 새가족 순장에게만 새가족을 배정할 수 있습니다."))
                                        .getLeaderUserId()
                )
                .collect(Collectors.toSet()));

        if (!newFamilyGroupLeaderUserIds.containsAll(assignedNewFamilyGroupLeaderUserIds)) {
            throw new IllegalStateException("코디 하위에 배정된 새가족 순장에게만 새가족 순원 및 새가족을 배정할 수 있습니다.");
        }

        // 결과 반환
        Set<Long> placedUserIdSet = new HashSet<>();

        placedUserIdSet.add(stumpLineUp.getSeniorPastorUserId());
        placedUserIdSet.addAll(stumpLineUp.getJuniorPastorUserIds());
        placedUserIdSet.addAll(stumpLineUp.getCodyUserIds());
        placedUserIdSet.addAll(smallGroupLeaderLineUps.stream().map(SmallGroupLeaderLineUp::getLeaderUserId).collect(Collectors.toList()));
        placedUserIdSet.addAll(newFamilyGroupLeaderLineUps.stream().map(NewFamilyGroupLeaderLineUp::getLeaderUserId).collect(Collectors.toList()));
        placedUserIdSet.addAll(smallGroupMemberLineUps.stream().map(SmallGroupMemberLineUp::getMemberUserId).collect(Collectors.toList()));
        placedUserIdSet.addAll(newFamilyGroupMemberLineUps.stream().map(NewFamilyGroupMemberLineUp::getMemberUserId).collect(Collectors.toList()));
        placedUserIdSet.addAll(newFamilies.stream().map(NewFamily::getUserId).collect(Collectors.toList()));

        return placedUserIdSet;
    }

    private void checkDiff(List<User> allUsers, Set<Long> activeUserIdSet, Set<Long> placedUserIdSet) {
        Map<Long, String> userIdNameMap = allUsers.stream()
                .map(it -> Map.entry(it.getId(), it.getName()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Set<Long> misplacedUserIdSet = new HashSet<>(placedUserIdSet);
        misplacedUserIdSet.removeAll(activeUserIdSet);
        Set<Long> notPlacedUserIdSet = new HashSet<>(activeUserIdSet);
        notPlacedUserIdSet.removeAll(placedUserIdSet);

        log.info("placed: {}", placedUserIdSet.size());
        log.info("misplaced: {}", misplacedUserIdSet.size());
        log.info("not placed: {}", notPlacedUserIdSet.size());

        if (!misplacedUserIdSet.isEmpty()) {
            Long misplacedUserId = misplacedUserIdSet.iterator().next();

            throw new IllegalStateException("비활성 유저가 라인업 되어 있습니다: " + userIdNameMap.get(misplacedUserId));
        }

        if (!notPlacedUserIdSet.isEmpty()) {
            Long notPlacedUserId = notPlacedUserIdSet.iterator().next();

            throw new IllegalStateException("라인업 누락 인원이 존재합니다: " + userIdNameMap.get(notPlacedUserId));
        }
    }
}
