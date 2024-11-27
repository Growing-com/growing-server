package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberUpstream {
    private final NewFamilyGroupMemberService newFamilyGroupMemberService;

    public void saveAll(List<NewFamilyGroupMember> newFamilyGroupMembers) {
        newFamilyGroupMemberService.saveAll(newFamilyGroupMembers);
    }

    public void deleteByNewFamilyGroupId(Long newFamilyGroupId) {
        newFamilyGroupMemberService.deleteByNewFamilyGroupId(newFamilyGroupId);
    }
}
