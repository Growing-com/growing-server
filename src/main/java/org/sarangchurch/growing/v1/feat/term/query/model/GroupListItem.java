package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GroupListItem {
    private final Long groupId;
    private final String leaderName;
    private final GroupType groupType;
}
