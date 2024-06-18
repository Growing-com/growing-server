package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
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
