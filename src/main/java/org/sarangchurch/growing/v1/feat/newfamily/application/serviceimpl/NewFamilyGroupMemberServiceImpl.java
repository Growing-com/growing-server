package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyGroupMemberWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupMemberServiceImpl implements NewFamilyGroupMemberService {
    private final NewFamilyGroupMemberFinder newFamilyGroupMemberFinder;
    private final NewFamilyGroupMemberWriter newFamilyGroupMemberWriter;

    @Override
    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberFinder.existsByUserIdAndTermId(userId, termId);
    }

    @Override
    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupMemberFinder.existsByUserIdInAndTermId(userIds, termId);
    }

    @Override
    public long countByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyGroupMemberFinder.countByNewFamilyGroupId(newFamilyGroupId);
    }

    @Override
    public List<NewFamilyGroupMember> saveAll(List<NewFamilyGroupMember> newFamilyGroupMembers) {
        return newFamilyGroupMemberWriter.saveAll(newFamilyGroupMembers);
    }

    @Override
    public void deleteByNewFamilyGroupId(Long newFamilyGroupId) {
        newFamilyGroupMemberWriter.deleteByNewFamilyGroupId(newFamilyGroupId);
    }
}
