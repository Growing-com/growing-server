package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

@Getter
@RequiredArgsConstructor
public class SmallGroupMemberListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
}