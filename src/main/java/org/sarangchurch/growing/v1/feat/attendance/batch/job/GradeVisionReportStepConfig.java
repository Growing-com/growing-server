package org.sarangchurch.growing.v1.feat.attendance.batch.job;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.attendance.batch.RequestWeekJobParameter;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport.GradeVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport.GradeVisionReportRepository;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendanceRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class GradeVisionReportStepConfig {
    private static final String STEP_NAME = "gradeVisionReportStep";

    private final UserService userService;
    private final NewFamilyService newFamilyService;

    private final StumpAttendanceRepository stumpAttendanceRepository;
    private final AttendanceRepository attendanceRepository;
    private final GradeVisionReportRepository gradeVisionReportRepository;

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;

    @JobScope
    @Bean(STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Sunday sunday = jobParameter.getSunday();
                    LocalDate date = sunday.getDate();

                    gradeVisionReportRepository.deleteByDate(date);

                    List<NewFamily> newFamilies = newFamilyService.findAllCurrent();
                    List<Long> newFamilyUserIds = newFamilies.stream().map(NewFamily::getUserId).collect(Collectors.toList());

                    Map<Integer, List<User>> gradeUsersMap = userService.findAllActive()
                            .stream()
                            .filter(it -> !newFamilyUserIds.contains(it.getId()))
                            .collect(Collectors.groupingBy(User::getGrade));

                    List<StumpAttendance> stumpAttendances = stumpAttendanceRepository.findByDate(date);
                    List<Attendance> attendances = attendanceRepository.findByDate(date);

                    List<GradeVisionReport> gradeVisionReports = gradeUsersMap.entrySet()
                            .stream()
                            .map(entry -> {
                                Integer grade = entry.getKey();
                                List<User> users = entry.getValue();
                                List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

                                long stumpAttendCount = stumpAttendances.stream()
                                        .filter(it -> userIds.contains(it.getUserId()) && it.statusEquals(AttendanceStatus.ATTEND))
                                        .count();

                                long attendCount = attendances.stream()
                                        .filter(it -> userIds.contains(it.getUserId()) && it.statusEquals(AttendanceStatus.ATTEND))
                                        .count();

                                return GradeVisionReport.builder()
                                        .date(date)
                                        .grade(grade)
                                        .total(users.size())
                                        .attend((int) (stumpAttendCount + attendCount))
                                        .build();
                            })
                            .collect(Collectors.toList());

                    gradeVisionReportRepository.saveAll(gradeVisionReports);

                    stepContribution.setExitStatus(new ExitStatus("SUCCESS", "비전 리포트가 갱신되었습니다. 날짜: " + date.toString()));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
