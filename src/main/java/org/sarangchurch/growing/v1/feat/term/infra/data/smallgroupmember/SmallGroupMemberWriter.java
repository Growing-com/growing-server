package org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberWriter {
    private final SmallGroupMemberRepository smallGroupMemberRepository;

    public void deleteBySmallGroupId(Long smallGroupId) {
        smallGroupMemberRepository.deleteBySmallGroupId(smallGroupId);
    }

    public List<SmallGroupMember> saveAll(List<SmallGroupMember> smallGroupMembers) {
        return smallGroupMemberRepository.saveAll(smallGroupMembers);
    }

    public SmallGroupMember save(SmallGroupMember smallGroupMember) {
        return smallGroupMemberRepository.save(smallGroupMember);
    }
}
