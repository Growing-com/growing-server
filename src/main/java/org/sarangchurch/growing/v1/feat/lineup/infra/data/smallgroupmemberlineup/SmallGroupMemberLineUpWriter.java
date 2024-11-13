package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberLineUpWriter {
    private final SmallGroupMemberLineUpRepository smallGroupMemberLineUpRepository;

    public void deleteBySmallGroupLeaderLineUpIdAndTermId(Long smallGroupLeaderLineUpId, Long termId) {
        smallGroupMemberLineUpRepository.deleteBySmallGroupLeaderLineUpIdAndTermId(smallGroupLeaderLineUpId, termId);
    }

    public void saveAll(List<SmallGroupMemberLineUp> smallGroupLeaderLineUps) {
        smallGroupMemberLineUpRepository.saveAll(smallGroupLeaderLineUps);
    }

    public void deleteBySmallGroupLeaderLineUpIdIn(List<Long> ids) {
        smallGroupMemberLineUpRepository.deleteBySmallGroupLeaderLineUpIdIn(ids);
    }
}
