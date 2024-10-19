package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupFinder {
    private final NewFamilyGroupRepository newFamilyGroupRepository;

    public long countByCodyId(Long codyId) {
        return newFamilyGroupRepository.countByCodyId(codyId);
    }
}
