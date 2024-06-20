package org.sarangchurch.growing.v2.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class LinedoutNewFamily {
    private final Long lineOutNewFamilyId;
    private final String name;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Gender gender;
    private final Integer grade;
    private final LocalDate visitDate;
    private final Map<String, Object> etc;
    private final String newFamilyGroupLeaderName;
    private final LocalDateTime lineoutDate;
}