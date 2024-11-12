package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup.SmallGroupMemberLineUpReader;
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
public class SmallGroupLeaderAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final SmallGroupMemberLineUpReader smallGroupMemberLineUpReader;
    private final NormalLineUpAvailableValidator normalLineUpAvailableValidator;
    private final SmallGroupLeaderLineUpWriter smallGroupLeaderLineUpWriter;

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
            throw new IllegalStateException("코디가 아닌 지체에게 일반 순장을 배정할 수 없습니다.");
        }

        List<SmallGroupLeaderLineUp> preExistingSmallGroupLeaderLineUps = smallGroupLeaderLineUpReader
                .findByCodyUserId(codyUser.getId());

        List<SmallGroupLeaderLineUp> newSmallGroupLeaderLineUps = candidateLeaderUsers.stream()
                .filter(candidateUser -> preExistingSmallGroupLeaderLineUps
                        .stream()
                        .filter(lineUp -> lineUp.getLeaderUserId().equals(candidateUser.getId()))
                        .findAny()
                        .isEmpty()
                )
                .map(it ->
                        SmallGroupLeaderLineUp.builder()
                                .termId(term.getId())
                                .codyUserId(codyUser.getId())
                                .leaderUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        List<SmallGroupLeaderLineUp> deleteLineUpCandidates = preExistingSmallGroupLeaderLineUps.stream()
                .filter(it -> candidateLeaderUsers
                        .stream()
                        .filter(user -> user.getId().equals(it.getLeaderUserId()))
                        .findAny()
                        .isEmpty())
                .collect(Collectors.toList());

        validateAssignedMemberExists(deleteLineUpCandidates);
        candidateLeaderUsers.forEach(user -> normalLineUpAvailableValidator.validateDutyAssignable(term, user, Duty.SMALL_GROUP_LEADER));

        List<Long> deleteIds = deleteLineUpCandidates.stream().map(SmallGroupLeaderLineUp::getId).collect(Collectors.toList());
        smallGroupLeaderLineUpWriter.deleteByIdIn(deleteIds);
        smallGroupLeaderLineUpWriter.saveAll(newSmallGroupLeaderLineUps);
    }

    private void validateAssignedMemberExists(List<SmallGroupLeaderLineUp> deleteLineUpCandidates) {
        List<Long> deleteIds = deleteLineUpCandidates.stream().map(SmallGroupLeaderLineUp::getId).collect(Collectors.toList());

        boolean memberLineUpExists =  smallGroupMemberLineUpReader.existsBySmallGroupLeaderLineUpIdIn(deleteIds);

        if (memberLineUpExists) {
            throw new IllegalStateException("담당하는 일반 순원이 있는 순장은 삭제할 수 없습니다.");
        }
    }
}
