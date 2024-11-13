package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup;

import java.util.List;

public interface NewFamilyLineUpRepository {
    void deleteByNewFamilyGroupLeaderLineUpIdAndTermId(Long newFamilyGroupLeaderLineUpId, Long termId);

    <S extends NewFamilyLineUp> List<S> saveAll(Iterable<S> entities);

    List<NewFamilyLineUp> findByTermId(Long termId);

    boolean existsByNewFamilyGroupLeaderLineUpIdIn(List<Long> lineUpIds);

    void deleteByNewFamilyGroupLeaderLineUpIdIn(List<Long> deleteIds);

    void deleteByTermIdAndNewFamilyIdIn(Long termId, List<Long> newFamilyIds);
}
