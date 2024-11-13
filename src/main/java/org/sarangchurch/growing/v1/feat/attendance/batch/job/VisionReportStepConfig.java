package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.attendance.AttendanceReader;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.newfamilyattendance.NewFamilyAttendanceReader;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.stumpattendance.StumpAttendanceReader;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport.VisionReportReader;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport.VisionReportWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.user.UserDownstream;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class VisionReportStepConfig {
    private static final String STEP_NAME = "visionReportStep";

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;

    private final UserDownstream userDownstream;
    private final AttendanceReader attendanceReader;
    private final StumpAttendanceReader stumpAttendanceReader;
    private final NewFamilyAttendanceReader newFamilyAttendanceReader;

    private final VisionReportReader visionReportReader;
    private final VisionReportWriter visionReportWriter;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((contribution, chunkContext) -> {
                    Sunday sunday = jobParameter.getSunday();
                    LocalDate sundayDate = sunday.getDate();

                    long activeUserCount = userDownstream.countActiveUsers();

                    long attendanceCount = attendanceReader.countByDate(sundayDate);
                    long stumpAttendanceCount = stumpAttendanceReader.countByDate(sundayDate);
                    long newFamilyAttendanceCount = newFamilyAttendanceReader.countByDate(sundayDate);

                    long totalAttendanceRegistered = attendanceCount + stumpAttendanceCount + newFamilyAttendanceCount;
                    double attendanceRegisterRate = (double) totalAttendanceRegistered / activeUserCount;

                    if (attendanceRegisterRate < 0.9d) {
                        contribution.setExitStatus(new ExitStatus("FAIL", "출석율이 90% 이상이어야 갱신할 수 있습니다. 현재: " + attendanceRegisterRate));
                    } else {
                        contribution.setExitStatus(new ExitStatus("SUCCESS", "비전 리포트가 갱신되었습니다. 날짜: " + sundayDate.toString()));

                        VisionReport visionReport = visionReportReader.findByDate(sundayDate)
                                .orElse(
                                        VisionReport.builder()
                                                .date(sundayDate)
                                                .totalActive((int) activeUserCount)
                                                .totalAttendanceRegistered((int) totalAttendanceRegistered)
                                                .build()
                                );

                        visionReport.update(activeUserCount, totalAttendanceRegistered);

                        visionReportWriter.save(visionReport);
                    }

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
