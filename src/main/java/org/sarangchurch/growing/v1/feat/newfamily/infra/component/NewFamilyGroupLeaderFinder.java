package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderFinder {
    private final NewFamilyGroupLeaderRepository newFamilyGroupLeaderRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderRepository.existsByUserIdAndTermId(userId, termId);
    }
}
