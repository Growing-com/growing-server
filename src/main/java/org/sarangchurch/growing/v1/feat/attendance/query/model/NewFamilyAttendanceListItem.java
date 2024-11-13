package org.sarangchurch.growing.v1.feat.attendance.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class NewFamilyAttendanceListItem {
    private final Long newFamilyId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String newFamilyGroupLeaderName;
    private final long totalAttendCount;
    private final long totalAbsentCount;
    private final List<NewFamilyAttendanceListItemAttendItem> attendanceItems;

    @Getter
    @RequiredArgsConstructor
    public static class NewFamilyAttendanceListItemAttendItem {
        @JsonIgnore
        private final Long newFamilyId;
        @JsonIgnore
        private final String name;
        @JsonIgnore
        private final Sex sex;
        @JsonIgnore
        private final Integer grade;
        @JsonIgnore
        private final String newFamilyGroupLeaderName;

        private final AttendanceStatus status;
        private final LocalDate date;
        private final String reason;
    }
}
