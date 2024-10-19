package org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader;

import java.util.List;

public interface SmallGroupLeaderRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    SmallGroupLeader save(SmallGroupLeader leader);

    void deleteById(Long smallGroupLeaderId);
}
