package org.sarangchurch.growing.attendance.batch.config.week;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.batch.parameter.RequestDateValidator;
import org.sarangchurch.growing.attendance.batch.parameter.RequestWeekJobParameter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WeeklyAttendanceJobConfig {
    private static final String JOB_NAME = "weeklyAttendanceJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final RequestDateValidator requestDateValidator;
    private final Step deleteExistingWeeklyAttendanceStep;
    private final Step weeklyPersonalAttendanceStep;
    private final Step weeklyManagerAttendanceStep;
    private final Step weeklyLeaderAttendanceStep;
    private final Step weeklyGradeAttendanceStep;
    private final Step weeklyEtcAttendanceStep;

    @JobScope
    @Bean(name = JOB_NAME + "Parameter")
    public RequestWeekJobParameter weekJobParameter(@Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info("[WeeklyAttendanceBatchJob] week: {}", requestDate);
        return new RequestWeekJobParameter(requestDate);
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .validator(requestDateValidator)
                .incrementer(new RunIdIncrementer())
                .start(deleteExistingWeeklyAttendanceStep)
                .next(weeklyPersonalAttendanceStep)
                .next(weeklyManagerAttendanceStep)
                .next(weeklyLeaderAttendanceStep)
                .next(weeklyGradeAttendanceStep)
                .next(weeklyEtcAttendanceStep)
                .build();
    }
}
