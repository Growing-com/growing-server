package org.sarangchurch.growing.v1.feat.term.infra.component.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroup.NewFamilyGroupDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroupmember.NewFamilyGroupMemberDownstream;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodyValidator {
    private final CodyFinder codyFinder;
    private final TermFinder termFinder;

    private final SmallGroupFinder smallGroupFinder;
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final NewFamilyGroupMemberDownstream newFamilyGroupMemberDownstream;


    public void validateAvailableById(Long codyId) {
        Cody cody = codyFinder.findByIdOrThrow(codyId);
        termFinder.findActiveByIdOrThrow(cody.getTermId());
    }

    public boolean areValidMemberUserIdsByCodyId(Long codyId, List<Long> userIds) {
        Cody cody = codyFinder.findByIdOrThrow(codyId);

        List<SmallGroup> smallGroups = smallGroupFinder.findByCodyId(cody.getId());
        List<Long> smallGroupIds = smallGroups.stream().map(SmallGroup::getId).collect(Collectors.toList());
        List<SmallGroupMember> smallGroupMembers = smallGroupMemberFinder.findBySmallGroupIdIn(smallGroupIds);

        List<NewFamilyGroup> newFamilyGroups = newFamilyGroupDownstream.findByCodyId(cody.getId());
        List<Long> newFamilyGroupIds = newFamilyGroups.stream().map(NewFamilyGroup::getId).collect(Collectors.toList());
        List<NewFamilyGroupMember> newFamilyGroupMembers = newFamilyGroupMemberDownstream.findByNewFamilyGroupIdIn(newFamilyGroupIds);

        Set<Long> validUserIds = new HashSet<>();
        validUserIds.addAll(smallGroups.stream().map(SmallGroup::getLeaderUserId).collect(Collectors.toList()));
        validUserIds.addAll(smallGroupMembers.stream().map(SmallGroupMember::getUserId).collect(Collectors.toList()));
        validUserIds.addAll(newFamilyGroups.stream().map(NewFamilyGroup::getLeaderUserId).collect(Collectors.toList()));
        validUserIds.addAll(newFamilyGroupMembers.stream().map(NewFamilyGroupMember::getUserId).collect(Collectors.toList()));

        return validUserIds.containsAll(userIds);
    }
}
