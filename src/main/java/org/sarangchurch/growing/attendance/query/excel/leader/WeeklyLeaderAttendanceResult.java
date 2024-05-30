package org.sarangchurch.growing.attendance.query.excel.leader;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeeklyLeaderAttendanceResult {
    private final Long managerId;
    private final String managerName;
    private final Long leaderId;
    private final String leaderName;
    private final Sex sex;
    private final Integer grade;
    private final String phoneNumber;
    private final List<WeeklyLeaderAttendanceItem> attendanceItems = new ArrayList<>();

    @Builder
    public WeeklyLeaderAttendanceResult(Long managerId, String managerName, Long leaderId, String leaderName, Sex sex, Integer grade, String phoneNumber) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.sex = sex;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
    }

    @Getter
    public static class WeeklyLeaderAttendanceItem {
        private final Week week;
        private final Long totalRegistered;
        private final Long totalAttendance;

        @Builder
        public WeeklyLeaderAttendanceItem(Week week, Long totalRegistered, Long totalAttendance) {
            this.week = week;
            this.totalRegistered = totalRegistered;
            this.totalAttendance = totalAttendance;
        }
    }
}
