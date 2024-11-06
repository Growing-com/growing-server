package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader.NewFamilyGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupmember.NewFamilyGroupMemberFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberValidator {
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final NewFamilyGroupLeaderFinder newFamilyGroupLeaderFinder;
    private final NewFamilyGroupMemberFinder newFamilyGroupMemberFinder;

    public boolean areValidUserIdsByNewFamilyGroupId(List<Long> userIds, Long newFamilyGroupId) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);
        NewFamilyGroupLeader leader = newFamilyGroupLeaderFinder.findByIdOrThrow(newFamilyGroup.getNewFamilyGroupLeaderId());
        List<NewFamilyGroupMember> members = newFamilyGroupMemberFinder.findByNewFamilyGroupId(newFamilyGroup.getId());

        List<Long> validUserIds = new ArrayList<>();
        validUserIds.add(leader.getUserId());
        validUserIds.addAll(members.stream().map(NewFamilyGroupMember::getUserId).collect(Collectors.toList()));

        return new HashSet<>(validUserIds).containsAll(userIds);
    }
}
