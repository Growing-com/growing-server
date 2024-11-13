package org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup.SmallGroupMemberLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberAssigner {
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final SmallGroupMemberLineUpWriter smallGroupMemberLineUpWriter;
    private final NormalLineUpAssignValidator normalLineUpAssignValidator;

    @Transactional
    public void assign(Term term, User leaderUser, List<User> memberUsers) {
        SmallGroupLeaderLineUp smallGroupLeaderLineUp = smallGroupLeaderLineUpReader.findByLeaderUserIdAndTermId(
                leaderUser.getId(),
                term.getId()
        );

        smallGroupMemberLineUpWriter.deleteBySmallGroupLeaderLineUpIdAndTermId(smallGroupLeaderLineUp.getId(), term.getId());

        memberUsers.forEach(user -> normalLineUpAssignValidator.validateDutyAssignable(term, user, Duty.SMALL_GROUP_MEMBER));

        List<SmallGroupMemberLineUp> smallGroupLeaderLineUps = memberUsers.stream()
                .map(it ->
                        SmallGroupMemberLineUp.builder()
                                .termId(term.getId())
                                .smallGroupLeaderLineUpId(smallGroupLeaderLineUp.getId())
                                .memberUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        smallGroupMemberLineUpWriter.saveAll(smallGroupLeaderLineUps);
    }
}
