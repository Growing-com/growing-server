package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderLineUpReader {
    private final NewFamilyGroupLeaderLineUpRepository newFamilyGroupLeaderLineUpRepository;

    public boolean existsByLeaderUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderLineUpRepository.existsByLeaderUserIdAndTermId(userId, termId);
    }

    public NewFamilyGroupLeaderLineUp findByLeaderUserIdAndTermId(Long leaderUserId, Long termId) {
        return newFamilyGroupLeaderLineUpRepository.findByLeaderUserIdAndTermId(leaderUserId, termId)
                .orElseThrow(() -> new IllegalArgumentException("새가족 순장 라인업 기록이 존재하지 않습니다."));
    }

    public List<NewFamilyGroupLeaderLineUp> findByTermId(Long termId) {
        return newFamilyGroupLeaderLineUpRepository.findByTermId(termId);
    }
}
