package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;

import java.util.List;

public interface NewFamilyGroupMemberService {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    long countByNewFamilyGroupId(Long newFamilyGroupId);

    List<NewFamilyGroupMember> saveAll(List<NewFamilyGroupMember> newFamilyGroupMembers);

}
