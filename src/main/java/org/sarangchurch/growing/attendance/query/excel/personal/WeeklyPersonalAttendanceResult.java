package org.sarangchurch.growing.attendance.query.excel.personal;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeeklyPersonalAttendanceResult {
    private final Long managerId;
    private final String managerName;
    private final String leaderName;
    private final String userName;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final List<WeeklyPersonalAttendanceItem> attendanceItems = new ArrayList<>();

    @Builder
    public WeeklyPersonalAttendanceResult(Long managerId, String managerName, String leaderName, String userName, Sex sex, Integer grade, String phoneNumber) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.leaderName = leaderName;
        this.userName = userName;
        this.sex = sex;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
    }

    @Getter
    @RequiredArgsConstructor
    public static class WeeklyPersonalAttendanceItem {
        private final Week week;
        private final AttendanceStatus status;
    }
}
