package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberFinder {
    private final NewFamilyGroupMemberRepository newFamilyGroupMemberRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberRepository.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupMemberRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public long countByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyGroupMemberRepository.countByNewFamilyGroupId(newFamilyGroupId);
    }

    public List<NewFamilyGroupMember> findByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyGroupMemberRepository.findByNewFamilyGroupId(newFamilyGroupId);
    }
}
