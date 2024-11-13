package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.GroupType;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

@Getter
@RequiredArgsConstructor
public class GroupListItem {
    private final Long groupId;
    private final String leaderName;
    private final GroupType groupType;
    private final Sex sex;
    private final Integer grade;
}
