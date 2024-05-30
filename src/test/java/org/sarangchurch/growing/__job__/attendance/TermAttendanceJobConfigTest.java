package org.sarangchurch.growing.__job__.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sarangchurch.growing.BatchTest;
import org.sarangchurch.growing.BatchTestConfig;
import org.sarangchurch.growing.DatabaseCleanup;
import org.sarangchurch.growing.DatabaseInit;
import org.sarangchurch.growing.attendance.batch.config.term.TermAttendanceJobConfig;
import org.sarangchurch.growing.attendance.batch.entity.term.TermAttendanceRepository;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendance;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendanceRepository;
import org.sarangchurch.growing.attendance.batch.parameter.RequestDateValidator;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {
        // 배치
        TermAttendanceJobConfig.class,
        // 공통
        RequestDateValidator.class,
        DatabaseCleanup.class,
        DatabaseInit.class,
        BatchTestConfig.class
})
public class TermAttendanceJobConfigTest extends BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private WeeklyPersonalAttendanceRepository weeklyPersonalAttendanceRepository;

    @Autowired
    private TermAttendanceRepository termAttendanceRepository;

    @DisplayName("[batch] 텀 출석율 생성 잡을 실행한다.")
    @Test
    void weeklyAttendanceJob() throws Exception {
        // given
        Week week = Week.previousSunday(LocalDate.of(2023, 9, 3));

        WeeklyPersonalAttendance weeklyPersonalAttendance = WeeklyPersonalAttendance.builder()
                .userId(3L)
                .userName("김세라")
                .userGrade(6)
                .userPhone("010-1234-1234")
                .userSex(Sex.FEMALE)
                .duty(Duty.MEMBER)
                .termId(1L)
                .teamId(1L)
                .managerId(1L)
                .managerName("이지우")
                .leaderId(2L)
                .leaderName("우상욱")
                .attendanceId(1L)
                .week(week)
                .status(AttendanceStatus.ATTEND)
                .etc("비고")
                .build();

        weeklyPersonalAttendanceRepository.save(weeklyPersonalAttendance);

        // when
        JobParameters jobParameters = new JobParameters(Map.of("termId", new JobParameter(1L)));
        jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertThat(termAttendanceRepository.findByTermId(1L)).isPresent();
    }
}
