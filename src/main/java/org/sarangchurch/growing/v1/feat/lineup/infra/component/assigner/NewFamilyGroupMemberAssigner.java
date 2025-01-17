package org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberAssigner {
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final NewFamilyGroupMemberLineUpWriter newFamilyGroupMemberLineUpWriter;
    private final NormalLineUpAssignValidator normalLineUpAssignValidator;
    
    @Transactional
    public void assign(Term term, User leaderUser, List<User> memberUsers) {
        NewFamilyGroupLeaderLineUp newFamilyGroupLeaderLineUp = newFamilyGroupLeaderLineUpReader.findByLeaderUserIdAndTermId(
                leaderUser.getId(),
                term.getId()
        );

        newFamilyGroupMemberLineUpWriter.deleteByNewFamilyGroupLeaderLineUpIdAndTermId(newFamilyGroupLeaderLineUp.getId(), term.getId());

        memberUsers.forEach(user -> normalLineUpAssignValidator.validateDutyAssignable(term, user, Duty.NEW_FAMILY_GROUP_MEMBER));

        List<NewFamilyGroupMemberLineUp> newFamilyGroupLeaderLineUps = memberUsers.stream()
                .map(it ->
                        NewFamilyGroupMemberLineUp.builder()
                                .termId(term.getId())
                                .newFamilyGroupLeaderLineUpId(newFamilyGroupLeaderLineUp.getId())
                                .memberUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        newFamilyGroupMemberLineUpWriter.saveAll(newFamilyGroupLeaderLineUps);
    }
}
