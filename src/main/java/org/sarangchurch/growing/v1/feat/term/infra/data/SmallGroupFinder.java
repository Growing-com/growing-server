package org.sarangchurch.growing.v1.feat.term.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupFinder {
    private final SmallGroupRepository smallGroupRepository;

    public SmallGroup findByIdOrThrow(Long id) {
        return smallGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));
    }

    public long countByCodyId(Long codyId) {
        return smallGroupRepository.countByCodyId(codyId);
    }

    public boolean existsByCodyIdAndSmallGroupLeaderId(Long codyId, Long smallGroupLeaderId) {
        return smallGroupRepository.existsByCodyIdAndSmallGroupLeaderId(codyId, smallGroupLeaderId);
    }
}
