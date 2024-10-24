package org.sarangchurch.growing.v1.feat.term.infra.component.newfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupLeaderUpstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupMemberUpstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupAppender {
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final NewFamilyGroupFinder newFamilyGroupFinder;

    private final NewFamilyGroupLeaderUpstream newFamilyGroupLeaderUpstream;
    private final NewFamilyGroupWriter newFamilyGroupWriter;
    private final NewFamilyGroupMemberUpstream newFamilyGroupMemberUpstream;

    @Transactional
    public void append(Long termId, Long codyId, Long leaderUserId, List<Long> memberUserIds) {
        Term term = termFinder.findActiveByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(leaderUserId);

        // 새가족 순장
        assignValidator.validateAssignable(term, user);

        NewFamilyGroupLeader savedNewFamilyGroupLeader = newFamilyGroupLeaderUpstream.save(
                NewFamilyGroupLeader.builder()
                        .termId(term.getId())
                        .userId(user.getId())
                        .build()
        );

        // 새가족 순모임
        boolean existsNewFamilyGroup = newFamilyGroupFinder.existsByCodyIdAndNewFamilyGroupLeaderId(codyId, savedNewFamilyGroupLeader.getId());

        if (existsNewFamilyGroup) {
            throw new IllegalStateException("이미 존재하는 새가족반입니다.");
        }

        NewFamilyGroup savedNewFamilyGroup = newFamilyGroupWriter.save(
                NewFamilyGroup.builder()
                        .termId(termId)
                        .codyId(codyId)
                        .newFamilyGroupLeaderId(savedNewFamilyGroupLeader.getId())
                        .build()
        );

        // 새가족 순원(새가족 아님!)
        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        assignValidator.validateAssignable(term, memberUsers);

        newFamilyGroupMemberUpstream.saveAll(
                memberUserIds.stream()
                        .map(it -> NewFamilyGroupMember.builder()
                                .termId(termId)
                                .newFamilyGroupId(savedNewFamilyGroup.getId())
                                .userId(it)
                                .build())
                        .collect(Collectors.toList())
        );
    }
}
