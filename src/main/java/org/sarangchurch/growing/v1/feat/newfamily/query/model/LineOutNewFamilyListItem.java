package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class LineOutNewFamilyListItem {
    private final Long lineOutNewFamilyId;
    private final String name;
    private final Sex sex;
    private final LocalDate birth;
    private final LocalDate visitDate;
    private final Integer grade;
    private final LocalDate lineOutDate;
}
