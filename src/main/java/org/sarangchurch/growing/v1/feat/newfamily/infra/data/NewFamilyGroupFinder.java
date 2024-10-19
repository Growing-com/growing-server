package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupFinder {
    private final NewFamilyGroupRepository newFamilyGroupRepository;

    public long countByCodyId(Long codyId) {
        return newFamilyGroupRepository.countByCodyId(codyId);
    }

    public NewFamilyGroup findByIdOrThrow(Long id) {
        return newFamilyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반입니다."));
    }
}
