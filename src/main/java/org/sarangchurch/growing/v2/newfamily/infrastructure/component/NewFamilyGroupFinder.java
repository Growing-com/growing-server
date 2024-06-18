package org.sarangchurch.growing.v2.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroup;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupFinder {
    private final NewFamilyGroupRepository newFamilyGroupRepository;

    public NewFamilyGroup findById(Long id) {
        return newFamilyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반입니다."));
    }
}
