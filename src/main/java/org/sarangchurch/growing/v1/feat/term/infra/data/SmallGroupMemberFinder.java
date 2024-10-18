package org.sarangchurch.growing.v1.feat.term.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;

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
}
