package org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupleader.SmallGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupValidator {
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final SmallGroupLeaderFinder smallGroupLeaderFinder;
    private final SmallGroupMemberFinder smallGroupMemberFinder;

    public void validateAvailable(List<Long> smallGroupIds) {
        List<SmallGroup> smallGroups = smallGroupFinder.findByIdIn(smallGroupIds);

        boolean containsInvalidId = smallGroupIds.stream()
                .anyMatch(smallGroupId ->
                        smallGroups.stream()
                                .filter(smallGroup -> smallGroup.getId().equals(smallGroupId))
                                .findAny()
                                .isEmpty()
                );

        if (containsInvalidId) {
            throw new IllegalArgumentException("존재하지 않는 일반 순모임이 포함되어 있습니다.");
        }

        Set<Long> termIds = smallGroups.stream()
                .map(SmallGroup::getTermId)
                .collect(Collectors.toSet());

        if (termIds.size() != 1) {
            throw new IllegalStateException("모든 가용한 순모임은 하나의 텀에만 속해있어야합니다.");
        }

        termFinder.findActiveByIdOrThrow(termIds.iterator().next());
    }

    public boolean areValidUserIdsBySmallGroupId(List<Long> userIds, Long smallGroupId) {
        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);
        SmallGroupLeader leader = smallGroupLeaderFinder.findByIdOrThrow(smallGroup.getSmallGroupLeaderId());
        List<SmallGroupMember> members = smallGroupMemberFinder.findBySmallGroupId(smallGroup.getId());

        List<Long> validUserIds = new ArrayList<>();
        validUserIds.add(leader.getUserId());
        validUserIds.addAll(members.stream().map(SmallGroupMember::getUserId).collect(Collectors.toList()));

        return new HashSet<>(validUserIds).containsAll(userIds);
    }
}
