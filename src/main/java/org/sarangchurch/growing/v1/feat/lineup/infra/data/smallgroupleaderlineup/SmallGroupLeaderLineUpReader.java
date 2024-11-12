package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderLineUpReader {
    private final SmallGroupLeaderLineUpRepository smallGroupLeaderLineUpRepository;

    public boolean existsByLeaderUserIdAndTermId(Long userId, Long termId) {
        return smallGroupLeaderLineUpRepository.existsByLeaderUserIdAndTermId(userId, termId);
    }

    public SmallGroupLeaderLineUp findByLeaderUserIdAndTermId(Long leaderUserId, Long termId) {
        return smallGroupLeaderLineUpRepository.findByLeaderUserIdAndTermId(leaderUserId, termId)
                .orElseThrow(() -> new IllegalArgumentException("일반 순장 라인업 기록이 존재하지 않습니다."));
    }

    public List<SmallGroupLeaderLineUp> findByTermId(Long termId) {
        return smallGroupLeaderLineUpRepository.findByTermId(termId);
    }

    public List<SmallGroupLeaderLineUp> findByCodyUserId(Long codyUserId) {
        return smallGroupLeaderLineUpRepository.findByCodyUserId(codyUserId);
    }
}
