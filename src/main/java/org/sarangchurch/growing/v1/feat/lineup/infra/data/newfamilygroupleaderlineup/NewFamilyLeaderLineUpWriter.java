package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLeaderLineUpWriter {
    private final NewFamilyGroupLeaderLineUpRepository newFamilyGroupLeaderLineUpRepository;

    public void deleteByCodyUserIdAndTermId(Long userId, Long termId) {
        newFamilyGroupLeaderLineUpRepository.deleteByCodyUserIdAndTermId(userId, termId);
    }

    public void saveAll(List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps) {
        newFamilyGroupLeaderLineUpRepository.saveAll(newFamilyGroupLeaderLineUps);
    }
}
