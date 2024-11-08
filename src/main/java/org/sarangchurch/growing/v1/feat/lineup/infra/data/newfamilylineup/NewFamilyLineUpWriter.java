package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpWriter {
    private final NewFamilyLineUpRepository newFamilyLineUpRepository;

    public void deleteByNewFamilyGroupLeaderLineUpIdAndTermId(Long newFamilyGroupLeaderLineUpId, Long termId) {
        newFamilyLineUpRepository.deleteByNewFamilyGroupLeaderLineUpIdAndTermId(newFamilyGroupLeaderLineUpId, termId);
    }

    public void saveAll(List<NewFamilyLineUp> newFamilyGroupLeaderLineUps) {
        newFamilyLineUpRepository.saveAll(newFamilyGroupLeaderLineUps);
    }
}
