package org.sarangchurch.growing.v1.feat.term.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TreeMemberListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final LocalDate birth;
    private final String leaderName;
    private final Duty duty;
}
