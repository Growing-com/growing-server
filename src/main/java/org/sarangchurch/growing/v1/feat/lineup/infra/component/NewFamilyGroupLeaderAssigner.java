package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final NewFamilyGroupMemberLineUpReader newFamilyGroupMemberLineUpReader;
    private final NewFamilyLineUpReader newFamilyLineUpReader;
    private final NormalLineUpAvailableValidator normalLineUpAvailableValidator;
    private final NewFamilyGroupLeaderLineUpWriter newFamilyGroupLeaderLineUpWriter;

    @Transactional
    public void assign(Term term, User codyUser, List<User> candidateLeaderUsers) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        if (!stumpLineUp.containsCody(codyUser)) {
            throw new IllegalStateException("코디가 아닌 지체에게 새가족 순장을 배정할 수 없습니다.");
        }

        List<NewFamilyGroupLeaderLineUp> preExistingNewFamilyGroupLeaderLineUps = newFamilyGroupLeaderLineUpReader
                .findByCodyUserId(codyUser.getId());

        List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps = candidateLeaderUsers.stream()
                .filter(candidateUser -> preExistingNewFamilyGroupLeaderLineUps
                        .stream()
                        .filter(lineUp -> lineUp.getLeaderUserId().equals(candidateUser.getId()))
                        .findAny()
                        .isEmpty()
                )
                .map(it ->
                        NewFamilyGroupLeaderLineUp.builder()
                                .termId(term.getId())
                                .codyUserId(codyUser.getId())
                                .leaderUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        List<NewFamilyGroupLeaderLineUp> deleteLineUpCandidates = preExistingNewFamilyGroupLeaderLineUps.stream()
                .filter(it -> candidateLeaderUsers
                        .stream()
                        .filter(user -> user.getId().equals(it.getLeaderUserId()))
                        .findAny()
                        .isEmpty())
                .collect(Collectors.toList());

        validateAssignedMemberExists(deleteLineUpCandidates);
        candidateLeaderUsers.forEach(user -> normalLineUpAvailableValidator.validateDutyAssignable(term, user, Duty.NEW_FAMILY_GROUP_LEADER));

        List<Long> deleteIds = deleteLineUpCandidates.stream().map(NewFamilyGroupLeaderLineUp::getId).collect(Collectors.toList());
        newFamilyGroupLeaderLineUpWriter.deleteByIdIn(deleteIds);
        newFamilyGroupLeaderLineUpWriter.saveAll(newFamilyGroupLeaderLineUps);
    }

    private void validateAssignedMemberExists(List<NewFamilyGroupLeaderLineUp> deleteLineUpCandidates) {
        List<Long> lineUpIds = deleteLineUpCandidates.stream().map(NewFamilyGroupLeaderLineUp::getId).collect(Collectors.toList());

        boolean memberLineUpExists =  newFamilyGroupMemberLineUpReader.existsByNewFamilyGroupLeaderLineUpIdIn(lineUpIds);

        if (memberLineUpExists) {
            throw new IllegalStateException("담당하는 새가족 순원이 있는 순장은 삭제할 수 없습니다.");
        }

        boolean newFamilyLineUpExists = newFamilyLineUpReader.existsByNewFamilyGroupLeaderLineUpIdIn(lineUpIds);

        if (newFamilyLineUpExists) {
            throw new IllegalStateException("담당하는 새가족이 있는 순장은 삭제할 수 없습니다.");
        }
    }
}
