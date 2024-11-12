package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpReader {
    private final NewFamilyLineUpRepository newFamilyLineUpRepository;

    public List<NewFamilyLineUp> findByTermId(Long termId) {
        return newFamilyLineUpRepository.findByTermId(termId);
    }

    public boolean existsByNewFamilyGroupLeaderLineUpIdIn(List<Long> lineUpIds) {
        return newFamilyLineUpRepository.existsByNewFamilyGroupLeaderLineUpIdIn(lineUpIds);
    }
}
