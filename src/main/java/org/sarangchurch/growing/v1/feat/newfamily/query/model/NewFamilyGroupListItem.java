package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

@Getter
@RequiredArgsConstructor
public class NewFamilyGroupListItem {
    private final Long newFamilyGroupId;
    private final String newFamilyGroupLeaderName;
    private final String codyName;
    private final Sex sex;
    private final Integer grade;
}
