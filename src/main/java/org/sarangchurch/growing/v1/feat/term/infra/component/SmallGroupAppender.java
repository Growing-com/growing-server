package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupAppender {
    private final UserDownstream userDownstream;
    private final SmallGroupLeaderRepository smallGroupLeaderRepository;
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;

    @Transactional
    public void append(Long termId, Long codyId, Long leaderUserId, List<Long> memberUserIds) {
        User user = userDownstream.findById(leaderUserId);

        if (!user.isActive()) {
            throw new IllegalStateException("순장이 활성 유저가 아닙니다.");
        }

        // 순장
        boolean existsSmallGroupLeader = smallGroupLeaderRepository.existsByTermIdAndUserId(termId, leaderUserId);

        if (existsSmallGroupLeader) {
            throw new IllegalStateException("이미 존재하는 순장입니다.");
        }

        SmallGroupLeader savedSmallGroupLeader = smallGroupLeaderRepository.save(SmallGroupLeader.builder()
                .termId(termId)
                .userId(leaderUserId)
                .build());

        // 순모임
        boolean existsSmallGroup = smallGroupRepository.existsByCodyIdAndSmallGroupLeaderId(codyId, savedSmallGroupLeader.getId());

        if (existsSmallGroup) {
            throw new IllegalStateException("이미 존재하는 순모임입니다.");
        }

        SmallGroup savedSmallGroup = smallGroupRepository.save(SmallGroup.builder()
                .termId(termId)
                .codyId(codyId)
                .smallGroupLeaderId(savedSmallGroupLeader.getId())
                .build());

        // 순원
        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        boolean isEveryUserActive = memberUsers.stream()
                .allMatch(User::isActive);

        if (!isEveryUserActive) {
            throw new IllegalStateException("순원중에 비활성 유저가 포함되어 있습니다.");
        }

        List<SmallGroupMember> smallGroupMembers = memberUserIds.stream()
                .map(it -> SmallGroupMember.builder()
                        .termId(termId)
                        .smallGroupId(savedSmallGroup.getId())
                        .userId(it)
                        .build()
                )
                .collect(Collectors.toList());

        smallGroupMemberRepository.deleteByTermIdAndUserIdIn(termId, memberUserIds);
        smallGroupMemberRepository.saveAll(smallGroupMembers);
    }
}
