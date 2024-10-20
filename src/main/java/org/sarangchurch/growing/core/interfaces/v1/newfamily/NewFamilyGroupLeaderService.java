package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;

import java.util.List;

public interface NewFamilyGroupLeaderService {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    NewFamilyGroupLeader save(NewFamilyGroupLeader newFamilyGroupLeader);

    void deleteById(Long newFamilyGroupLeaderId);
}
