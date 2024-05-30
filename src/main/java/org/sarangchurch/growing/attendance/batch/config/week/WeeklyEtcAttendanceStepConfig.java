package org.sarangchurch.growing.attendance.batch.config.week;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendance;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendanceEtc;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendance;
import org.sarangchurch.growing.attendance.batch.parameter.RequestWeekJobParameter;
import org.sarangchurch.growing.attendance.batch.entity.user.UserRepository;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendanceRepository;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendanceRepository;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WeeklyEtcAttendanceStepConfig {
    private static final String STEP_NAME = "weeklyEtcAttendanceStep";

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;
    private final WeeklyAttendanceRepository weeklyAttendanceRepository;
    private final WeeklyPersonalAttendanceRepository weeklyPersonalAttendanceRepository;
    private final UserRepository userRepository;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Week week = jobParameter.getWeek();

                    WeeklyAttendance weeklyAttendance = weeklyAttendanceRepository
                            .findByWeek(jobParameter.getWeek())
                            .orElseGet(() -> weeklyAttendanceRepository.save(new WeeklyAttendance(week)));

                    List<WeeklyPersonalAttendance> personalAttendances = weeklyPersonalAttendanceRepository.findByWeek(week);

                    WeeklyAttendanceEtc weeklyAttendanceEtc = WeeklyAttendanceEtc.builder()
                            .totalRegistered((long) personalAttendances.size())
                            .totalAttendance(personalAttendances.stream().filter(WeeklyPersonalAttendance::isAttend).count())
                            .totalOnline(personalAttendances.stream().filter(WeeklyPersonalAttendance::isOnline).count())
                            .totalAbsent(personalAttendances.stream().filter(WeeklyPersonalAttendance::isAbsent).count())
                            .maleRegistered(personalAttendances.stream().filter(WeeklyPersonalAttendance::isMale).count())
                            .maleAttendance(personalAttendances.stream().filter(it -> it.isMale() && it.isAttendOrOnline()).count())
                            .femaleRegistered(personalAttendances.stream().filter(WeeklyPersonalAttendance::isFemale).count())
                            .femaleAttendance(personalAttendances.stream().filter(it -> it.isFemale() && it.isAttendOrOnline()).count())
                            .newComerRegistered(personalAttendances.stream().filter(it -> it.getDuty() == Duty.NEW_COMER).count())
                            .newComerAttendance(personalAttendances.stream().filter(it -> it.getDuty() == Duty.NEW_COMER && it.isAttendOrOnline()).count())
                            .newVisited(userRepository.countByVisitDate(week.getWeek()))
                            .build();

                    weeklyAttendance.setAttendanceEtc(weeklyAttendanceEtc);

                    weeklyAttendanceRepository.save(weeklyAttendance);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
