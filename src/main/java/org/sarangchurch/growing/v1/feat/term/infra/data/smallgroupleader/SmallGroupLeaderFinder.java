package org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupleader;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
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

    public SmallGroupLeader findByIdOrThrow(Long smallGroupLeaderId) {
        return smallGroupLeaderRepository.findById(smallGroupLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순장입니다."));
    }
}
