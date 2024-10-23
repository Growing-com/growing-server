package org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<SmallGroup> findByIdIn(List<Long> ids) {
        return smallGroupRepository.findByIdIn(ids);
    }

    public List<SmallGroup> findByCodyId(Long codyId) {
        return smallGroupRepository.findByCodyId(codyId);
    }

    public List<SmallGroup> findByIdInOrThrow(List<Long> ids) {
        List<SmallGroup> smallGroups = smallGroupRepository.findByIdIn(ids);

        if (smallGroups.size() != ids.size()) {
            throw new IllegalArgumentException("존재하지 않는 순모임이 포함되어 있습니다.");
        }

        return smallGroups;
    }
}