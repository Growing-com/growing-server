package org.sarangchurch.growing.attendance.batch.entity.week;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.core.types.Week;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WeeklyAttendanceTest {
    @DisplayName("[unit] 주간 출석은 코디별로 출석 수를 합친다.")
    @Test
    void test1() {
        // given
        WeeklyAttendance weeklyAttendance = new WeeklyAttendance(Week.previousSunday(LocalDate.now()));

        weeklyAttendance.mergeManager(List.of(
                new WeeklyManagerAttendance(1L, "이지우", 10L, 2L)
        ));

        // when
        weeklyAttendance.mergeManager(List.of(
                new WeeklyManagerAttendance(1L, "이지우", 10L, 3L)
        ));

        // then
        List<WeeklyManagerAttendance> managerAttendances = weeklyAttendance.getManagerAttendances().getManagerAttendances();
        WeeklyManagerAttendance weeklyManagerAttendance = managerAttendances.stream()
                .filter(el -> el.getManagerId().equals(1L))
                .findAny()
                .orElseThrow();

        assertThat(weeklyManagerAttendance.getManagerId()).isEqualTo(1L);
        assertThat(weeklyManagerAttendance.getManagerName()).isEqualTo("이지우");
        assertThat(weeklyManagerAttendance.getTotalRegistered()).isEqualTo(10L);
        assertThat(weeklyManagerAttendance.getTotalAttendance()).isEqualTo(5L);
    }
}
