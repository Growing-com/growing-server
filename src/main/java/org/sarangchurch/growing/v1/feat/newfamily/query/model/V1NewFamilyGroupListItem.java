package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class V1NewFamilyGroupListItem {
    private final Long newFamilyGroupId;
    private final String newFamilyGroupLeaderName;
}
