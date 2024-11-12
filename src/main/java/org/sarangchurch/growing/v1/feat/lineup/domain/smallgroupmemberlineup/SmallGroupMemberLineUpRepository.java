package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup;

import java.util.List;

public interface SmallGroupMemberLineUpRepository {
    boolean existsByMemberUserIdAndTermId(Long userId, Long termId);

    void deleteBySmallGroupLeaderLineUpIdAndTermId(Long smallGroupLeaderLineUpId, Long termId);

    <S extends SmallGroupMemberLineUp> List<S> saveAll(Iterable<S> entities);

    List<SmallGroupMemberLineUp> findByTermId(Long termId);

    boolean existsBySmallGroupLeaderLineUpIdIn(List<Long> smallGroupLeaderLineUpIds);
}
