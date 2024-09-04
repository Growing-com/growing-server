package org.sarangchurch.growing.v2.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class LinedoutNewFamily {
    private final Long lineOutNewFamilyId;
    private final String name;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Sex sex;
    private final Integer grade;
    private final LocalDate visitDate;
    private final NewFamilyEtc etc;
    private final String newFamilyGroupLeaderName;
    private final LocalDateTime lineoutAt;
}
