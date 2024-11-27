package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup;

import java.util.List;
import java.util.Optional;

public interface NewFamilyGroupRepository {
    Optional<NewFamilyGroup> findById(Long id);

    long countByCodyId(Long codyId);

    NewFamilyGroup save(NewFamilyGroup newFamilyGroup);

    void deleteById(Long newFamilyGroupId);

    List<NewFamilyGroup> findByTermId(Long termId);

    boolean existsByCodyIdAndLeaderUserId(Long codyId, Long leaderUserId);

    boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId);

    boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId);

    List<NewFamilyGroup> findByCodyId(Long codyId);
}
