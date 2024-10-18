package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader;

import java.util.Optional;

public interface NewFamilyGroupLeaderRepository {
    Optional<NewFamilyGroupLeader> findById(Long id);

    boolean existsByUserIdAndTermId(Long userId, Long termId);
}
