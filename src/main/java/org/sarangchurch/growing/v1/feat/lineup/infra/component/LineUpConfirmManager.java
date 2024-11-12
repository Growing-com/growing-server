package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v1.feat.lineup.domain.LineUpIntegrityChecker;
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
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyGroupUpstream;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyUpstream;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.term.TermUpstream;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private final TermUpstream termUpstream;
    private final NewFamilyUpstream newFamilyUpstream;
    private final NewFamilyGroupUpstream newFamilyGroupUpstream;

    @Transactional
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
        List<Long> newFamilyIds = newFamilyLineUps.stream()
                .map(NewFamilyLineUp::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyDownstream.findByIdInOrThrow(newFamilyIds);

        boolean areValidNewFamilies = newFamilies.stream()
                .allMatch(newFamily -> newFamilyDownstream.isNewFamilyByUserId(newFamily.getUserId()));

        if (!areValidNewFamilies) {
            throw new IllegalStateException("새가족이 아닌 지체가 포함되어 있습니다. 일반 라인업을 진행해주세요.");
        }

        // 로깅 START
        log.info("active user: {}", activeUsers.size());
        log.info("senior pastor: {}", stumpLineUp.getSeniorPastorUserId() != null ? 1 : 0);
        log.info("junior pastor: {}", stumpLineUp.getJuniorPastorUserIds().size());
        log.info("cody: {}", stumpLineUp.getCodyUserIds().size());
        log.info("small grp leader: {}", smallGroupLeaderLineUps.size());
        log.info("new fam grp leader: {}", newFamilyGroupLeaderLineUps.size());
        log.info("small grp member: {}", smallGroupMemberLineUps.size());
        log.info("new fam grp member: {}", newFamilyGroupMemberLineUps.size());
        log.info("new fam: {}", newFamilyLineUps.size());
        // 로깅 END

        LineUpIntegrityChecker lineUpIntegrityChecker = new LineUpIntegrityChecker(
                stumpLineUp,
                smallGroupLeaderLineUps,
                newFamilyGroupLeaderLineUps,
                smallGroupMemberLineUps,
                newFamilyGroupMemberLineUps,
                newFamilyLineUps,
                newFamilies
        );

        Set<Long> activeUserIdSet = activeUsers.stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        lineUpIntegrityChecker.check(allUsers, activeUserIdSet);

        newFamilyUpstream.processLineUps(newFamilyLineUps);
        newFamilyGroupUpstream.processLineUps(newFamilyGroupLeaderLineUps, newFamilyGroupMemberLineUps);
        termUpstream.processLineUpsAndStartTerm(stumpLineUp, smallGroupLeaderLineUps, smallGroupMemberLineUps);
    }

}
