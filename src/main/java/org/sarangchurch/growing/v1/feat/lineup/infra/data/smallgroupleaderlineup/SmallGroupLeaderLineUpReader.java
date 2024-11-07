package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderLineUpReader {
    private final SmallGroupLeaderLineUpRepository smallGroupLeaderLineUpRepository;

    public boolean existsByLeaderUserIdAndTermId(Long userId, Long termId) {
        return smallGroupLeaderLineUpRepository.existsByLeaderUserIdAndTermId(userId, termId);
    }
}
