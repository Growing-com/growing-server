package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupUpdater {
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;
    private final UserDownstream userDownstream;

    @Transactional
    public void update(Long smallGroupId, List<Long> memberUserIds) {
        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        List<User> memberUsers = userDownstream.findByIdIn(memberUserIds);

        if (memberUsers.size() != memberUserIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다");
        }

        // memberUsers are active

        List<SmallGroupMember> smallGroupMembers = memberUserIds.stream()
                .map(it -> SmallGroupMember.builder()
                        .termId(smallGroup.getTermId())
                        .smallGroupId(smallGroupId)
                        .userId(it)
                        .build()
                )
                .collect(Collectors.toList());

        smallGroupMemberRepository.deleteBySmallGroupId(smallGroupId);
        smallGroupMemberRepository.saveAll(smallGroupMembers);
    }
}
