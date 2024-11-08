package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup;

import java.util.List;

public interface NewFamilyGroupLeaderLineUpRepository {
    boolean existsByLeaderUserIdAndTermId(Long userId, Long termId);

    void deleteByCodyUserIdAndTermId(Long userId, Long termId);

    <S extends NewFamilyGroupLeaderLineUp> List<S> saveAll(Iterable<S> entities);
}
