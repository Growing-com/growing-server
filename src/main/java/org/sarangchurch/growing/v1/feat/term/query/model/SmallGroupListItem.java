package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

@Getter
@RequiredArgsConstructor
public class SmallGroupListItem {
    private final Long smallGroupId;
    private final String codyName;
    private final String leaderName;
    private final Sex sex;
    private final Integer grade;
}
