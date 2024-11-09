package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup;

import java.util.List;

public interface NewFamilyGroupMemberLineUpRepository {
    boolean existsByMemberUserIdAndTermId(Long userId, Long termId);

    void deleteByNewFamilyGroupLeaderLineUpIdAndTermId(Long newFamilyGroupLeaderLineUpId, Long termId);

    <S extends NewFamilyGroupMemberLineUp> List<S> saveAll(Iterable<S> entities);

    List<NewFamilyGroupMemberLineUp> findByTermId(Long termId);
}
