package org.sarangchurch.growing.v1.feat.newfamily.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.v1.core.interfaces.common.Sex;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class NewFamilyListItem {
    private final Long newFamilyId;
    private final String name;
    private final Sex sex;
    private final String phoneNumber;
    private final LocalDate birth;
    private final LocalDate visitDate;
    private final Integer grade;
    private final NewFamilyEtc etc;
    private final String newFamilyGroupLeaderName;
    // smallGroupLeaderName: O, promoteDate: X -> (가)라인업 상태, 등반전
    // smallGroupLeaderName: O, promoteDate: O -> 라인업 + 등반 완료
    private final String smallGroupLeaderName;
    private final LocalDate promoteDate;
}
