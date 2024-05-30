package org.sarangchurch.growing.attendance.batch.config.week;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendance;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyManagerAttendance;
import org.sarangchurch.growing.attendance.batch.parameter.RequestWeekJobParameter;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyAttendanceRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WeeklyManagerAttendanceStepConfig {
    private static final String STEP_NAME = "weeklyManagerAttendanceStep";
    private static final int CHUNK_SIZE = 1000;

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;
    private final WeeklyAttendanceRepository weeklyAttendanceRepository;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .<WeeklyManagerAttendance, WeeklyManagerAttendance>chunk(CHUNK_SIZE)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Reader")
    public RepositoryItemReader<WeeklyManagerAttendance> reader() {
        return new RepositoryItemReaderBuilder<WeeklyManagerAttendance>()
                .name(STEP_NAME + "Reader")
                .repository(weeklyAttendanceRepository)
                .methodName("findManagerGroupedByWeek")
                .pageSize(CHUNK_SIZE)
                .arguments(jobParameter.getWeek())
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Writer")
    public ItemWriter<WeeklyManagerAttendance> writer() {
        return managerAttendances -> {
            WeeklyAttendance weeklyAttendance = weeklyAttendanceRepository
                    .findByWeek(jobParameter.getWeek())
                    .orElseGet(() -> weeklyAttendanceRepository.save(new WeeklyAttendance(jobParameter.getWeek())));

            weeklyAttendance.mergeManager(managerAttendances);

            weeklyAttendanceRepository.save(weeklyAttendance);
        };
    }
}
