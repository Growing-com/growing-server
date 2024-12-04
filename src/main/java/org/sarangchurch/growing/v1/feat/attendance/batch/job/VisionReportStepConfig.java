package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport.VisionReportReader;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport.VisionReportWriter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class VisionReportStepConfig {
    private static final String STEP_NAME = "visionReportStep";

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;

    private final UserService userService;
    private final NewFamilyService newFamilyService;

    private final AttendanceRepository attendanceRepository;
    private final StumpAttendanceRepository stumpAttendanceRepository;
    private final NewFamilyAttendanceRepository newFamilyAttendanceRepository;

    private final VisionReportReader visionReportReader;
    private final VisionReportWriter visionReportWriter;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Sunday sunday = jobParameter.getSunday();
                    LocalDate sundayDate = sunday.getDate();

                    long active = userService.countActiveUsers();

                    List<Attendance> memberAttendances = attendanceRepository.findByDate(sundayDate);
                    List<StumpAttendance> stumpAttendances = stumpAttendanceRepository.findByDate(sundayDate);
                    List<NewFamilyAttendance> newFamilyAttendances = newFamilyAttendanceRepository.findByDate(sundayDate);

                    long registered = memberAttendances.size() + stumpAttendances.size() + newFamilyAttendances.size();
                    double registerRate = ((double) registered / active) * 100;

                    if (registerRate < 90) {
                        stepContribution.setExitStatus(new ExitStatus("FAIL", "출석율이 90% 이상이어야 갱신할 수 있습니다. 현재: " + String.format("%.2f%%", registerRate)));

                        return RepeatStatus.FINISHED;
                    }

                    VisionReport visionReport = visionReportReader.findByDate(sundayDate)
                            .orElse(VisionReport.fromDate(sundayDate));

                    long newFamily = newFamilyService.countTotalCurrent();

                    long memberAttendCount = memberAttendances.stream().filter(it -> it.statusEquals(AttendanceStatus.ATTEND)).count();
                    long stumpAttendCount = stumpAttendances.stream().filter(it -> it.statusEquals(AttendanceStatus.ATTEND)).count();
                    long newFamilyAttendCount = newFamilyAttendances.stream().filter(it -> it.statusEquals(AttendanceStatus.ATTEND)).count();

                    visionReport.setActive((int) active);
                    visionReport.setRegistered((int) registered);
                    visionReport.setTotalAttend((int) (memberAttendCount + stumpAttendCount + newFamilyAttendCount));
                    visionReport.setNewFamily((int) newFamily);
                    visionReport.setNewFamilyAttend((int) newFamilyAttendCount);

                    visionReportWriter.save(visionReport);

                    stepContribution.setExitStatus(new ExitStatus("SUCCESS", "비전 리포트가 갱신되었습니다. 날짜: " + sundayDate.toString()));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
