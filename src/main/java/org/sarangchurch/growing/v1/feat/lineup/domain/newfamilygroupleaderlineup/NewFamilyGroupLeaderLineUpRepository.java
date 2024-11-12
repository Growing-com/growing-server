package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup;

import java.util.List;
import java.util.Optional;

public interface NewFamilyGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);

    <S extends NewFamilyGroupLeaderLineUp> List<S> saveAll(Iterable<S> entities);

    Optional<NewFamilyGroupLeaderLineUp> findByLeaderUserIdAndTermId(Long leaderUserId, Long termId);

    List<NewFamilyGroupLeaderLineUp> findByTermId(Long termId);

    List<NewFamilyGroupLeaderLineUp> findByCodyUserId(Long codyUserId);

    void deleteByIdIn(List<Long> deleteIds);
}
