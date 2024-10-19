package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import java.util.List;

public interface NewFamilyGroupLeaderService {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);
}
