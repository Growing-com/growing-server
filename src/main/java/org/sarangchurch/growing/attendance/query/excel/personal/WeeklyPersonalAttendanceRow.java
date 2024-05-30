package org.sarangchurch.growing.attendance.query.excel.personal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

@Getter
@RequiredArgsConstructor
public class WeeklyPersonalAttendanceRow {
    private final Long managerId;
    private final String managerName;
    private final String leaderName;
    private final Long userId;
    private final String userName;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final Week week;
    private final AttendanceStatus status;
}
