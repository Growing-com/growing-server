package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderLineUpReader {
    private final NewFamilyGroupLeaderLineUpRepository newFamilyGroupLeaderLineUpRepository;

    public boolean existsByLeaderUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderLineUpRepository.existsByLeaderUserIdAndTermId(userId, termId);
    }
}
