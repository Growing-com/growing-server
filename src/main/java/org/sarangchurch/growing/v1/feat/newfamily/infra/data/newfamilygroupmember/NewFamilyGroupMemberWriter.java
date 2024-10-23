package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberWriter {
    private final NewFamilyGroupMemberRepository newFamilyGroupMemberRepository;

    public List<NewFamilyGroupMember> saveAll(List<NewFamilyGroupMember> newFamilyGroupMembers) {
        return newFamilyGroupMemberRepository.saveAll(newFamilyGroupMembers);
    }

    public void deleteByNewFamilyGroupId(Long newFamilyGroupId) {
        newFamilyGroupMemberRepository.deleteByNewFamilyGroupId(newFamilyGroupId);
    }
}
