package org.sarangchurch.growing.v2.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyFinder {
    private final NewFamilyRepository newFamilyRepository;

    public NewFamily findById(Long id) {
        return newFamilyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));
    }

    public boolean existsByIds(List<Long> ids) {
        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(ids);

        return newFamilies.size() == ids.size();
    }

    public List<NewFamily> findByIdIn(List<Long> ids) {
        return newFamilyRepository.findByIdIn(ids);
    }
}
