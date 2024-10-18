package org.sarangchurch.growing.core.interfaces.v1.newfamily;

public interface NewFamilyGroupMemberService {
    boolean existsByUserIdAndTermId(Long userId, Long termId);
}
