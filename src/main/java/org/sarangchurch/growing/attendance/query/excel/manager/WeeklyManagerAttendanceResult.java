package org.sarangchurch.growing.attendance.query.excel.manager;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.core.types.Week;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeeklyManagerAttendanceResult {
    private final Long managerId;
    private final String managerName;
    private final List<WeeklyManagerAttendanceItem> attendanceItems = new ArrayList<>();

    public WeeklyManagerAttendanceResult(Long managerId, String managerName) {
        this.managerId = managerId;
        this.managerName = managerName;
    }

    @Getter
    public static class WeeklyManagerAttendanceItem {
        private final Week week;
        private final Long totalRegistered;
        private final Long totalAttendance;

        @Builder
        public WeeklyManagerAttendanceItem(Week week, Long totalRegistered, Long totalAttendance) {
            this.week = week;
            this.totalRegistered = totalRegistered;
            this.totalAttendance = totalAttendance;
        }
    }
}
