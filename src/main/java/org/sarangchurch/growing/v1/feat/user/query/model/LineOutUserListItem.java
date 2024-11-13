package org.sarangchurch.growing.v1.feat.user.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class LineOutUserListItem {
    private final Long lineOutUserId;
    private final String name;
    private final Sex sex;
    private final LocalDate birth;
    private final Integer grade;
    private final LocalDate lineOutDate;
    private final String reason;
}
