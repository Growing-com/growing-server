package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader;

import java.util.List;
import java.util.Optional;

public interface NewFamilyGroupLeaderRepository {
    Optional<NewFamilyGroupLeader> findById(Long id);

    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    NewFamilyGroupLeader save(NewFamilyGroupLeader newFamilyGroupLeader);

    void deleteById(Long newFamilyGroupLeaderId);
}
