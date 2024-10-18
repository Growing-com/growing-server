package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupMemberWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupUpdater {
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final SmallGroupMemberWriter smallGroupMemberWriter;

    @Transactional
    public void update(Long smallGroupId, List<Long> memberUserIds) {
        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);

        Term term = termFinder.findActiveByIdOrThrow(smallGroup.getTermId());

        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다");
        }

        smallGroupMemberWriter.deleteBySmallGroupId(smallGroupId);
        assignValidator.validateAssignable(term, memberUsers);
        smallGroupMemberWriter.saveAll(memberUserIds.stream()
                .map(it -> SmallGroupMember.builder()
                        .termId(smallGroup.getTermId())
                        .smallGroupId(smallGroupId)
                        .userId(it)
                        .build()
                )
                .collect(Collectors.toList()));
    }
}
