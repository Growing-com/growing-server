package org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberLineUpReader {
    private final SmallGroupMemberLineUpRepository smallGroupMemberLineUpRepository;

    public boolean existsByMemberUserIdAndTermId(Long userId, Long termId) {
        return smallGroupMemberLineUpRepository.existsByMemberUserIdAndTermId(userId, termId);
    }
}
