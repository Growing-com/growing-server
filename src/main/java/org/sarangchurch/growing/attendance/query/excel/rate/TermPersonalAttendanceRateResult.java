package org.sarangchurch.growing.attendance.query.excel.rate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class TermPersonalAttendanceRateResult {
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<TermPersonalAttendanceRateItem> attendanceRateItems;

    @Getter
    @RequiredArgsConstructor
    public static class TermPersonalAttendanceRateItem {
        private final String userName;
        private final Sex sex;
        private final Integer grade;
        private final String phoneNumber;
        private final Long totalWeekPassed;
        private final Long totalAttend;
        private final Long totalOnline;
        private final Long totalAbsent;
    }
}
