package org.sarangchurch.growing.attendance.batch.config.week;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.batch.parameter.RequestWeekJobParameter;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendanceRepository;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendanceRepository;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeleteExistingWeeklyAttendanceStepConfig {
    private static final String STEP_NAME = "deleteExistingWeeklyAttendanceStep";

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;
    private final WeeklyAttendanceRepository weeklyAttendanceRepository;
    private final WeeklyPersonalAttendanceRepository weeklyPersonalAttendanceRepository;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    Week week = jobParameter.getWeek();

                    weeklyPersonalAttendanceRepository.deleteByWeek(week);
                    weeklyAttendanceRepository.deleteByWeek(week);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
