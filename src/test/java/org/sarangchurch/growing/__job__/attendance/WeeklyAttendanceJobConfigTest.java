package org.sarangchurch.growing.__job__.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sarangchurch.growing.BatchTest;
import org.sarangchurch.growing.BatchTestConfig;
import org.sarangchurch.growing.DatabaseCleanup;
import org.sarangchurch.growing.DatabaseInit;
import org.sarangchurch.growing.attendance.batch.config.week.*;
import org.sarangchurch.growing.attendance.batch.entity.week.*;
import org.sarangchurch.growing.attendance.batch.parameter.RequestDateValidator;
import org.sarangchurch.growing.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {
        // 배치
        WeeklyAttendanceJobConfig.class,
        DeleteExistingWeeklyAttendanceStepConfig.class,
        WeeklyPersonalAttendanceStepConfig.class,
        WeeklyManagerAttendanceStepConfig.class,
        WeeklyLeaderAttendanceStepConfig.class,
        WeeklyGradeAttendanceStepConfig.class,
        WeeklyEtcAttendanceStepConfig.class,
        // 공통
        RequestDateValidator.class,
        DatabaseCleanup.class,
        DatabaseInit.class,
        BatchTestConfig.class
})
public class WeeklyAttendanceJobConfigTest extends BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private WeeklyAttendanceRepository weeklyAttendanceRepository;

    @Autowired
    private WeeklyPersonalAttendanceRepository weeklyPersonalAttendanceRepository;

    @DisplayName("[batch] 주간 출석 통계 생성 잡을 실행한다.")
    @Test
    void weeklyAttendanceJob() throws Exception {
        // given
        Week week = Week.previousSunday(LocalDate.of(2023, 9, 3));
        givenAttendanceOfWeekAndCount(week, 500L);

        // when
        JobParameters jobParameters = new JobParameters(Map.of("requestDate", new JobParameter(week.getWeek().toString())));
        jobLauncherTestUtils.launchJob(jobParameters);

        // then1
        WeeklyAttendance weeklyAttendance = weeklyAttendanceRepository.findByWeek(week).get();

        assertThat(weeklyAttendance).isNotNull();
        assertThat(weeklyAttendance.getAttendanceEtc()).isEqualTo(WeeklyAttendanceEtc.builder()
                .totalRegistered(453L)
                .totalAttendance(453L)
                .totalOnline(0L)
                .totalAbsent(0L)
                .maleRegistered(213L)
                .maleAttendance(213L)
                .femaleRegistered(240L)
                .femaleAttendance(240L)
                .newComerRegistered(17L)
                .newComerAttendance(17L)
                .newVisited(8L)
                .build());

        // then2
        assertThat(weeklyPersonalAttendanceRepository.findAll()).hasSize(453);
        assertThat(weeklyPersonalAttendanceRepository.findAll()).allMatch(it -> it.getWeek().equals(week));

        // then3
        Page<WeeklyManagerAttendance> weeklyManagerAttendances = weeklyAttendanceRepository.findManagerGroupedByWeek(
                week,
                Pageable.unpaged()
        );

        assertThat(weeklyManagerAttendances).hasSize(8);

        // then4
        Page<WeeklyLeaderAttendance> weeklyLeaderAttendances = weeklyAttendanceRepository.findLeaderGroupedByWeek(
                week,
                Pageable.unpaged()
        );

        assertThat(weeklyLeaderAttendances).hasSize(41);

        // then5
        Page<WeeklyGradeAttendance> weeklyGradeAttendances = weeklyAttendanceRepository.findGradeGroupedByWeek(
                week,
                Pageable.unpaged()
        );

        assertThat(weeklyGradeAttendances).hasSize(15);
    }

    private void givenAttendanceOfWeekAndCount(Week week, Long count) {
        List<Attendance> attendances = LongStream.rangeClosed(1, count)
                .mapToObj(el -> Attendance.builder()
                        .teamMemberId(el)
                        .week(week)
                        .status(AttendanceStatus.ATTEND)
                        .build()
                )
                .collect(Collectors.toList());

        attendanceRepository.saveAll(attendances);
    }
}
