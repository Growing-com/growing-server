package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupMemberUpstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupUpdater {
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final NewFamilyGroupMemberUpstream newFamilyGroupMemberUpstream;

    @Transactional
    public void update(Long newFamilyGroupId, List<Long> memberUserIds) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupDownstream.findByIdOrThrow(newFamilyGroupId);

        Term term = termFinder.findActiveByIdOrThrow(newFamilyGroup.getTermId());

        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다");
        }

        newFamilyGroupMemberUpstream.deleteByNewFamilyGroupId(newFamilyGroupId);
        assignValidator.validateAssignable(term, memberUsers);
        newFamilyGroupMemberUpstream.saveAll(
                memberUserIds.stream()
                        .map(it -> NewFamilyGroupMember.builder()
                                .termId(term.getId())
                                .newFamilyGroupId(newFamilyGroupId)
                                .userId(it)
                                .build())
                        .collect(Collectors.toList())
        );
    }
}
