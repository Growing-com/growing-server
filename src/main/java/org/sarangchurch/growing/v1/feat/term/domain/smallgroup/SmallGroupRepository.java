package org.sarangchurch.growing.v1.feat.term.domain.smallgroup;

import java.util.List;
import java.util.Optional;

public interface SmallGroupRepository {
    List<SmallGroup> findByIdIn(List<Long> ids);

    SmallGroup save(SmallGroup smallGroup);

    boolean existsByCodyIdAndLeaderUserId(Long codyId, Long leaderUserId);

    Optional<SmallGroup> findById(Long id);

    void deleteById(Long id);

    long countByCodyId(Long codyId);

    List<SmallGroup> findByCodyId(Long codyId);

    boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId);

    boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId);
}
