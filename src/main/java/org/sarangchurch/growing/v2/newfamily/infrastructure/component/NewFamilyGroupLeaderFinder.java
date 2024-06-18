package org.sarangchurch.growing.v2.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupLeader;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupLeaderRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderFinder {
    private final NewFamilyGroupLeaderRepository newFamilyGroupLeaderRepository;

    public NewFamilyGroupLeader findById(Long id) {
        return newFamilyGroupLeaderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반 순장입니다"));
    }
}
