package org.sarangchurch.growing.v1.feat.term.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderFinder {
    private final SmallGroupLeaderRepository smallGroupLeaderRepository;

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return smallGroupLeaderRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return smallGroupLeaderRepository.existsByUserIdAndTermId(userId, termId);
    }
}
