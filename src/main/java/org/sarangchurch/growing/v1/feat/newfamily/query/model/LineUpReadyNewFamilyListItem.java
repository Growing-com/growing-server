package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LineUpReadyNewFamilyListItem {
    private final Long newFamilyId;
    private final String name;
    private final Sex sex;
    private final String phoneNumber;
    private final LocalDate birth;
    private final LocalDate visitDate;
    private final Integer grade;
    private final NewFamilyEtc etc;
    private final String newFamilyGroupLeaderName;
    private final String smallGroupLeaderName;
    private final List<Long> temporarySmallGroupIds;
}
