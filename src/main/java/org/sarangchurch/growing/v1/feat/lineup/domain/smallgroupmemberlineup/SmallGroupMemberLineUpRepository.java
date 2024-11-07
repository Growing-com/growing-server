package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup;

public interface SmallGroupMemberLineUpRepository {
    boolean existsByMemberUserIdAndTermId(Long userId, Long termId);
}
