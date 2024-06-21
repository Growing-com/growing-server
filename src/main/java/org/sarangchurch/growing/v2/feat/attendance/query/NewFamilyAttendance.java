package org.sarangchurch.growing.v2.feat.attendance.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class NewFamilyAttendance {
    private final Long newFamilyId;
    private final LocalDateTime newFamilyCreatedAt;
    private final String name;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Gender gender;
    private final Integer grade;
    private final LocalDate visitDate;
    private final Map<String, Object> etc;
    private final String newFamilyGroupLeaderName;
    private final String smallGroupLeaderName;
    private final String promotedSmallGroupLeaderName;
    private final LocalDate promoteDate;

    private List<NewFamilyAttendanceItem> attendanceItems = new ArrayList<>();

    public void setAttendanceItems(List<NewFamilyAttendanceItem> attendanceItems) {
        this.attendanceItems = attendanceItems;
    }
}
