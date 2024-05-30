package org.sarangchurch.growing.__e2e__.attendance.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.attendance.AttendanceAssertions;
import org.sarangchurch.growing.__e2e__.attendance.AttendanceSteps;

import java.time.LocalDate;

public class RegisterAttendanceTest extends AcceptanceTest {

    @DisplayName("[e2e] 권한이 있는 순모임 출석을 변경할 수 있다.")
    @Test
    void test1() {
        long 문은선_순모임 = 20L;
        long 문은선_조원 = 202L;

        CommonAssertions.요청에_성공한다(
                AttendanceSteps.민섭이가_출석을_등록한다(
                        LocalDate.of(2023, 10, 1),
                        문은선_순모임,
                        문은선_조원
                ));
    }

    @DisplayName("[e2e] 텀 날짜가 안 맞으면 팀의 출석을 변경할 수 없다.")
    @Test
    void test2() {
        long 문은선_순모임 = 20L;
        long 문은선_조원 = 202L;

        AttendanceAssertions.허용되지_않은_주차다(
                AttendanceSteps.민섭이가_출석을_등록한다(
                        LocalDate.of(2023, 8, 1),
                        문은선_순모임,
                        문은선_조원
                ));
    }

    @DisplayName("[e2e] 권한이 없는 순모임의 출석을 변경할 수 없다.")
    @Test
    void test3() {
        long 강예은_순모임 = 18L;
        long 방초원_조원 = 251L;

        AttendanceAssertions.순모임_권한이_없다(
                AttendanceSteps.민섭이가_출석을_등록한다(
                        LocalDate.of(2023, 10, 1),
                        강예은_순모임,
                        방초원_조원
                ));
    }

    @DisplayName("[e2e] 순모임에 소속되지 않은 순원의 출석을 변경할 수 없다.")
    @Test
    void test4() {
        long 문은선_순모임 = 20L;
        long 방초원_조원 = 251L;

        AttendanceAssertions.순모임에_소속되지_않은_순원이_있다(
                AttendanceSteps.민섭이가_출석을_등록한다(
                        LocalDate.of(2023, 10, 1),
                        문은선_순모임,
                        방초원_조원
                ));
    }

    @DisplayName("[e2e] 교역자는 모든 순모임의 출석을 변경할 수 있다.")
    @Test
    void test5() {
        long 문은선_순모임 = 20L;
        long 문은선_조원 = 202L;

        CommonAssertions.요청에_성공한다(
                AttendanceSteps.교역자가_출석을_등록한다(
                        LocalDate.of(2023, 10, 1),
                        문은선_순모임,
                        문은선_조원
                ));
    }
}
