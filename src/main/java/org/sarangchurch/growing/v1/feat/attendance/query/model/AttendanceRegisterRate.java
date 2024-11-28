package org.sarangchurch.growing.v1.feat.attendance.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AttendanceRegisterRate {
    // 전체
    private final Long totalActive;
    private final Long totalRegistered;
    // 그루터기
    private final Long totalStump;
    private final Long totalStumpRegistered;
    // 순장
    private final Long totalSmallGroupLeaders;
    private final Long totalSmallGroupLeadersRegistered;
    private final Long totalNewFamilyGroupLeaders;
    private final Long totalNewFamilyGroupLeadersRegistered;
    // 순원
    private final Long totalSmallGroupMembers;
    private final Long totalSmallGroupMembersRegistered;
    private final Long totalNewFamilyGroupMembers;
    private final Long totalNewFamilyGroupMembersRegistered;
    // 새가족
    private final Long totalNewFamilies;
    private final Long totalNewFamiliesRegistered;
}
