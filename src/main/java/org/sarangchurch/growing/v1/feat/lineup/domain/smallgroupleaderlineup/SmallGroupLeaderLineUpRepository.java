package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup;

import java.util.List;
import java.util.Optional;

public interface SmallGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);

    <S extends SmallGroupLeaderLineUp> List<S> saveAll(Iterable<S> smallGroupLeaderLineUps);

    Optional<SmallGroupLeaderLineUp> findByLeaderUserIdAndTermId(Long leaderUserId, Long termId);

    List<SmallGroupLeaderLineUp> findByTermId(Long termId);

    List<SmallGroupLeaderLineUp> findByCodyUserId(Long codyUserId);

    void deleteByIdIn(List<Long> deleteIds);
}
