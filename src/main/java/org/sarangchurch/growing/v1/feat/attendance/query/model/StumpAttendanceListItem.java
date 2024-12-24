package org.sarangchurch.growing.v1.feat.attendance.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class StumpAttendanceListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final StumpAttendanceListItemAttendItem attendanceItem;

    @Getter
    @RequiredArgsConstructor
    public static class StumpAttendanceListItemAttendItem {
        @JsonIgnore
        private final Long userId;

        private final AttendanceStatus status;
        private final LocalDate date;
        private final String reason;
    }
}
