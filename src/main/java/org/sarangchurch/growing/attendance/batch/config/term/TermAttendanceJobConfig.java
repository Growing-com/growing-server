package org.sarangchurch.growing.attendance.batch.config.term;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.batch.entity.term.TermAttendance;
import org.sarangchurch.growing.attendance.batch.entity.term.TermPersonalAttendance;
import org.sarangchurch.growing.attendance.batch.entity.term.TermAttendanceRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TermAttendanceJobConfig {
    private static final String JOB_NAME = "termAttendanceJob";
    private static final String STEP_NAME = "termAttendanceStep";
    private static final int CHUNK_SIZE = 1000;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final TermAttendanceRepository termAttendanceRepository;

    @Bean(name = JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(beforeStep(null))
                .next(step())
                .build();
    }

    @JobScope
    @Bean(name = "before" + STEP_NAME)
    public Step beforeStep(@Value("#{jobParameters[termId]}") Long termId) {
        return stepBuilderFactory.get("before" + STEP_NAME)
                .tasklet((stepContribution, chunkContext) -> {
                    termAttendanceRepository.deleteByTermId(termId);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @JobScope
    @Bean(name = STEP_NAME)
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .<TermPersonalAttendance, TermPersonalAttendance>chunk(CHUNK_SIZE)
                .reader(reader(null))
                .writer(writer(null))
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Reader")
    public JdbcCursorItemReader<TermPersonalAttendance> reader(@Value("#{jobParameters[termId]}") Long termId) {
        return new JdbcCursorItemReaderBuilder<TermPersonalAttendance>()
                .name(STEP_NAME + "Reader")
                .fetchSize(CHUNK_SIZE)
                .dataSource(dataSource)
                .sql(sql())
                .preparedStatementSetter(ps -> ps.setLong(1, termId))
                .rowMapper(rowMapper())
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Writer")
    public ItemWriter<TermPersonalAttendance> writer(@Value("#{jobParameters[termId]}") Long termId) {
        return personalAttendances -> {
            TermAttendance termAttendance = termAttendanceRepository
                    .findByTermId(termId)
                    .orElseGet(() -> termAttendanceRepository.save(TermAttendance.fromTerm(termId)));

            termAttendance.merge(personalAttendances);

            termAttendanceRepository.save(termAttendance);
        };
    }

    private String sql() {
        return "select  pa.user_id                                       as user_id, " +
                "       count(*)                                         as total_week_passed, " +
                "       count(case when pa.status = 'ATTEND' then 1 end) as total_attend, " +
                "       count(case when pa.status = 'ONLINE' then 1 end) as total_online, " +
                "       count(case when pa.status = 'ABSENT' then 1 end) as total_absent " +
                "from weekly_personal_attendance pa " +
                "         inner join term t on t.id = pa.term_id " +
                "where term_id = ? " +
                "group by user_id;";
    }

    private RowMapper<TermPersonalAttendance> rowMapper() {
        return (rs, rowNum) -> TermPersonalAttendance.builder()
                .userId(rs.getLong("user_id"))
                .totalWeekPassed(rs.getLong("total_week_passed"))
                .totalAttend(rs.getLong("total_attend"))
                .totalOnline(rs.getLong("total_online"))
                .totalAbsent(rs.getLong("total_absent"))
                .build();
    }
}
