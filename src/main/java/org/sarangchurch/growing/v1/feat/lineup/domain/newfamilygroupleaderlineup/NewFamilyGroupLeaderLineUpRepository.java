package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup;

public interface NewFamilyGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);
}
