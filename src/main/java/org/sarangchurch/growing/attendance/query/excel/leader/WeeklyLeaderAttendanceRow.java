package org.sarangchurch.growing.attendance.query.excel.leader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

@Getter
@RequiredArgsConstructor
public class WeeklyLeaderAttendanceRow {
    private final Long managerId;
    private final String managerName;
    private final Long leaderId;
    private final String leaderName;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final Week week;
    private final Long totalRegistered;
    private final Long totalAttendance;
}
