package org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupAppender {
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final SmallGroupFinder smallGroupFinder;

    private final SmallGroupMemberWriter smallGroupMemberWriter;
    private final SmallGroupWriter smallGroupWriter;

    @Transactional
    public void append(Long termId, Long codyId, Long leaderUserId, List<Long> memberUserIds) {
        Term term = termFinder.findActiveByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(leaderUserId);

        assignValidator.validateAssignable(term, user);

        // 순모임
        boolean existsSmallGroup = smallGroupFinder.existsByCodyIdAndLeaderUserId(codyId, leaderUserId);

        if (existsSmallGroup) {
            throw new IllegalStateException("이미 존재하는 순모임입니다.");
        }

        SmallGroup savedSmallGroup = smallGroupWriter.save(
                SmallGroup.builder()
                        .termId(termId)
                        .codyId(codyId)
                        .leaderUserId(leaderUserId)
                        .build()
        );

        // 순원
        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        assignValidator.validateAssignable(term, memberUsers);

        smallGroupMemberWriter.saveAll(memberUserIds.stream()
                .map(it -> SmallGroupMember.builder()
                        .termId(termId)
                        .smallGroupId(savedSmallGroup.getId())
                        .userId(it)
                        .build()
                )
                .collect(Collectors.toList()));
    }
}
