package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DutyDistributionCount {
    private final Long totalCount;
    private final Long pastorCount;
    private final Long codyCount;
    private final Long smallGroupLeaderCount;
    private final Long newFamilyGroupLeaderCount;
    private final Long smallGroupMemberCount;
    private final Long newFamilyMemberCount;
    private final Long newFamilyCount;
    private final Long notPlacedCount;
}
