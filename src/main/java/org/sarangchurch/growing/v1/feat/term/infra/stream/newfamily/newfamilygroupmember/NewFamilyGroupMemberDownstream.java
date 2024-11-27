package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberDownstream {
    private final NewFamilyGroupMemberService newFamilyGroupMemberService;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberService.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupMemberService.existsByUserIdInAndTermId(userIds, termId);
    }

    public long countByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyGroupMemberService.countByNewFamilyGroupId(newFamilyGroupId);
    }

    public List<NewFamilyGroupMember> findByNewFamilyGroupIdIn(List<Long> newFamilyGroupIds) {
        return newFamilyGroupMemberService.findByNewFamilyGroupIdIn(newFamilyGroupIds);
    }
}
