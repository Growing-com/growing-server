package org.sarangchurch.growing.v1.feat.user.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.term.domain.Duty;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class UserListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final LocalDate birth;
    private final String etc;
    private final Duty duty;
    private final String leaderName;
}
