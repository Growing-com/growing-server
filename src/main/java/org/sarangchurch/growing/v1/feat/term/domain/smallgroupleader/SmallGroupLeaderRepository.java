package org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader;

import java.util.List;
import java.util.Optional;

public interface SmallGroupLeaderRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    SmallGroupLeader save(SmallGroupLeader leader);

    void deleteById(Long smallGroupLeaderId);

    Optional<SmallGroupLeader> findById(Long id);

    List<SmallGroupLeader> findByTermId(Long termId);
}
