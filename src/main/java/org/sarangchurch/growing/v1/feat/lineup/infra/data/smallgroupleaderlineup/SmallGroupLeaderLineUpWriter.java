package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderLineUpWriter {
    private final SmallGroupLeaderLineUpRepository smallGroupLeaderLineUpRepository;

    public void saveAll(List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps) {
        smallGroupLeaderLineUpRepository.saveAll(smallGroupLeaderLineUps);
    }

    public void deleteByIdIn(List<Long> deleteIds) {
        smallGroupLeaderLineUpRepository.deleteByIdIn(deleteIds);
    }
}
