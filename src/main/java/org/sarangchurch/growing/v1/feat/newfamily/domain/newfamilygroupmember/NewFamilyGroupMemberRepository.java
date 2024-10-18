package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember;

public interface NewFamilyGroupMemberRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);
}
