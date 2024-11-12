package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderLineUpWriter {
    private final NewFamilyGroupLeaderLineUpRepository newFamilyGroupLeaderLineUpRepository;

    public void saveAll(List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps) {
        newFamilyGroupLeaderLineUpRepository.saveAll(newFamilyGroupLeaderLineUps);
    }

    public void deleteByIdIn(List<Long> deleteIds) {
        newFamilyGroupLeaderLineUpRepository.deleteByIdIn(deleteIds);
    }
}
