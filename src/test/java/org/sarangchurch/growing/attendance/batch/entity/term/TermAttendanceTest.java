package org.sarangchurch.growing.attendance.batch.entity.term;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TermAttendanceTest {
    @DisplayName("[unit] 텀 출석은 유저별로 출석 정보들을 합친다.")
    @Test
    void name() {
        TermAttendance termAttendance = TermAttendance.fromTerm(1L);

        termAttendance.merge(List.of(
                termPersonalAttendanceByUserId(1L),
                termPersonalAttendanceByUserId(1L),
                termPersonalAttendanceByUserId(2L),
                termPersonalAttendanceByUserId(2L),
                termPersonalAttendanceByUserId(3L)
        ));

        assertThat(termAttendance.getTermPersonalAttendances().getPersonalAttendances()).hasSize(3);
    }

    private TermPersonalAttendance termPersonalAttendanceByUserId(Long userId) {
        return TermPersonalAttendance.builder()
                .userId(userId)
                .totalWeekPassed(23L)
                .totalAttend(20L)
                .totalAbsent(3L)
                .totalOnline(0L)
                .build();
    }
}
