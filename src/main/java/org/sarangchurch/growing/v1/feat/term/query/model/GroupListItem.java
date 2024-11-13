package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.GroupType;

@Getter
@RequiredArgsConstructor
public class GroupListItem {
    private final Long groupId;
    private final String leaderName;
    private final GroupType groupType;
}
