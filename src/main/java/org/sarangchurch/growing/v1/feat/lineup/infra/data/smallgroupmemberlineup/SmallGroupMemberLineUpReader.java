package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberLineUpReader {
    private final SmallGroupMemberLineUpRepository smallGroupMemberLineUpRepository;

    public boolean existsByMemberUserIdAndTermId(Long userId, Long termId) {
        return smallGroupMemberLineUpRepository.existsByMemberUserIdAndTermId(userId, termId);
    }

    public List<SmallGroupMemberLineUp> findByTermId(Long termId) {
        return smallGroupMemberLineUpRepository.findByTermId(termId);
    }

    public boolean existsBySmallGroupLeaderLineUpIdIn(List<Long> smallGroupLeaderLineUpIds) {
        return smallGroupMemberLineUpRepository.existsBySmallGroupLeaderLineUpIdIn(smallGroupLeaderLineUpIds);
    }
}
