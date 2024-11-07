package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup;

public interface SmallGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);
}
