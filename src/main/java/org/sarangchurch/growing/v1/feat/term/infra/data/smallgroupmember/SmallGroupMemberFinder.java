package org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberFinder {
    private final SmallGroupMemberRepository smallGroupMemberRepository;

    public long countBySmallGroupId(Long smallGroupId) {
        return smallGroupMemberRepository.countBySmallGroupId(smallGroupId);
    }

    public SmallGroupMember findByIdOrThrow(Long id) {
        return smallGroupMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순원입니다."));
    }

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return smallGroupMemberRepository.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return smallGroupMemberRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public boolean existsByUserIdAndSmallGroupId(Long userId, Long smallGroupId) {
        return smallGroupMemberRepository.existsByUserIdAndSmallGroupId(userId, smallGroupId);
    }

    public Optional<SmallGroupMember> findByUserIdAndTermId(Long userId, Long termId) {
        return smallGroupMemberRepository.findByUserIdAndTermId(userId, termId);
    }

    public List<SmallGroupMember> findBySmallGroupIdIn(List<Long> smallGroupIds) {
        return smallGroupMemberRepository.findBySmallGroupIdIn(smallGroupIds);
    }
}
