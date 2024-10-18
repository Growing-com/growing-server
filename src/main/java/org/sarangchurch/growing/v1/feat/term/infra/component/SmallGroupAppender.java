package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.*;
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
    private final SmallGroupLeaderWriter smallGroupLeaderWriter;
    private final SmallGroupWriter smallGroupWriter;

    @Transactional
    public void append(Long termId, Long codyId, Long leaderUserId, List<Long> memberUserIds) {
        Term term = termFinder.findActiveByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(leaderUserId);

        // 순장
        assignValidator.validateAssignable(term, user);

        SmallGroupLeader savedSmallGroupLeader = smallGroupLeaderWriter.save(SmallGroupLeader.builder()
                .termId(term.getId())
                .userId(user.getId())
                .build());

        // 순모임
        boolean existsSmallGroup = smallGroupFinder.existsByCodyIdAndSmallGroupLeaderId(codyId, savedSmallGroupLeader.getId());

        if (existsSmallGroup) {
            throw new IllegalStateException("이미 존재하는 순모임입니다.");
        }

        SmallGroup savedSmallGroup = smallGroupWriter.save(SmallGroup.builder()
                .termId(termId)
                .codyId(codyId)
                .smallGroupLeaderId(savedSmallGroupLeader.getId())
                .build());

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
