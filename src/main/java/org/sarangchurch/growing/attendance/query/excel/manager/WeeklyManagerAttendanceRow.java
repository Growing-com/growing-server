package org.sarangchurch.growing.attendance.query.excel.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;

@Getter
@RequiredArgsConstructor
public class WeeklyManagerAttendanceRow {
    private final Long managerId;
    private final String managerName;
    private final Week week;
    private final Long totalRegistered;
    private final Long totalAttendance;
}
