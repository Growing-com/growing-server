package org.sarangchurch.growing.attendance.query.excel.grade;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.core.types.Week;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeeklyGradeAttendanceResult {
    private final Integer grade;
    private final List<WeeklyGradeAttendanceItem> attendanceItems = new ArrayList<>();

    public WeeklyGradeAttendanceResult(Integer grade) {
        this.grade = grade;
    }

    @Getter
    public static class WeeklyGradeAttendanceItem {
        private final Week week;
        private final Long totalRegistered;
        private final Long totalAttendance;

        @Builder
        public WeeklyGradeAttendanceItem(Week week, Long totalRegistered, Long totalAttendance) {
            this.week = week;
            this.totalRegistered = totalRegistered;
            this.totalAttendance = totalAttendance;
        }
    }
}
