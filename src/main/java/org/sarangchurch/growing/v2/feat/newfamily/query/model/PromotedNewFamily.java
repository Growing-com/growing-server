package org.sarangchurch.growing.v2.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Sex;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyEtc;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PromotedNewFamily {
    private final Long newFamilyId;
    private final String name;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Sex sex;
    private final Integer grade;
    private final LocalDate visitDate;
    private final NewFamilyEtc etc;
    private final String newFamilyGroupLeaderName;
    private final String smallGroupLeaderName;
    private final String promotedSmallGroupLeaderName;
    private final LocalDate promoteDate;
}
