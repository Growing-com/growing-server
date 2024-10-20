package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember;

import java.util.List;

public interface NewFamilyGroupMemberRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    long countByNewFamilyGroupId(Long newFamilyGroupId);

    <S extends NewFamilyGroupMember> List<S> saveAll(Iterable<S> newFamilyGroupMembers);

    void deleteByNewFamilyGroupId(Long newFamilyGroupId);
}
