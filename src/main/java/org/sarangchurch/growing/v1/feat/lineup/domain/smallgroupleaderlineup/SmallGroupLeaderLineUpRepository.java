package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup;

import java.util.List;

public interface SmallGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);

    <S extends SmallGroupLeaderLineUp> List<S> saveAll(Iterable<S> smallGroupLeaderLineUps);

    void deleteByCodyUserIdAndTermId(Long codyId, Long termId);
}
