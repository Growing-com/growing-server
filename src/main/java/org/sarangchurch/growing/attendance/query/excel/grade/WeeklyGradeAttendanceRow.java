package org.sarangchurch.growing.attendance.query.excel.grade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;

@Getter
@RequiredArgsConstructor
public class WeeklyGradeAttendanceRow {
    private final Integer grade;
    private final Week week;
    private final Long totalRegistered;
    private final Long totalAttendance;
}
