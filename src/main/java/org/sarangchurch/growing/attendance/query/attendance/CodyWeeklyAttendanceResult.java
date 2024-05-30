package org.sarangchurch.growing.attendance.query.attendance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

@Getter
@RequiredArgsConstructor
public class CodyWeeklyAttendanceResult {
    private final String leaderName;
    private final Long teamId;
    private final Long teamMemberId;
    private final String userName;
    private final Integer grade;
    private final Sex sex;
    private final Long attendanceId;
    private final AttendanceStatus status;
    private final Week week;
    private final String etc;
}
