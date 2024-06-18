package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
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
