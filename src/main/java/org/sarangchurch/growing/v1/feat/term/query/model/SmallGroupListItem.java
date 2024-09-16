package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SmallGroupListItem {
    private final Long smallGroupId;
    private final String smallGroupLeaderName;
}
