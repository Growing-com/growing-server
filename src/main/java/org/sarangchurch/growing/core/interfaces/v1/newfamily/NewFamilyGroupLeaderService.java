package org.sarangchurch.growing.core.interfaces.v1.newfamily;

public interface NewFamilyGroupLeaderService {
    boolean existsByUserIdAndTermId(Long userId, Long termId);
}
