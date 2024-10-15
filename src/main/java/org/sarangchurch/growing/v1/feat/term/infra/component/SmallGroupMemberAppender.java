package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberAppender {
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;

    public void append(Long userId, Long smallGroupId) {
        boolean alreadyExists = smallGroupMemberRepository.existsByUserIdAndSmallGroupId(userId, smallGroupId);

        if (alreadyExists) {
            throw new IllegalArgumentException("이미 순원으로 등록되어있습니다.");
        }

        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다"));

        SmallGroupMember smallGroupMember = SmallGroupMember.builder()
                .termId(smallGroup.getTermId())
                .userId(userId)
                .smallGroupId(smallGroupId)
                .build();

        smallGroupMemberRepository.save(smallGroupMember);
    }
}
