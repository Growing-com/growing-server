package org.sarangchurch.growing.v2.feat.attendance.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class NewFamilyAttendanceItem {
    private final LocalDate date;
    private final AttendanceStatus status;
    private final String reason;
    private final Long newFamilyId;
}
