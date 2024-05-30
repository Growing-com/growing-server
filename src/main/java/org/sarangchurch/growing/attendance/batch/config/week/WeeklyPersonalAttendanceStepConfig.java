package org.sarangchurch.growing.attendance.batch.config.week;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendance;
import org.sarangchurch.growing.attendance.batch.entity.week.WeeklyPersonalAttendanceRepository;
import org.sarangchurch.growing.attendance.batch.parameter.RequestWeekJobParameter;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class WeeklyPersonalAttendanceStepConfig {
    private static final String STEP_NAME = "weeklyPersonalAttendanceStep";
    private static final int CHUNK_SIZE = 1000;

    private final StepBuilderFactory stepBuilderFactory;
    private final RequestWeekJobParameter jobParameter;
    private final DataSource dataSource;
    private final WeeklyPersonalAttendanceRepository weeklyPersonalAttendanceRepository;

    @JobScope
    @Bean(name = STEP_NAME)
    public Step personalAttendanceStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<WeeklyPersonalAttendance, WeeklyPersonalAttendance>chunk(CHUNK_SIZE)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Reader")
    public JdbcCursorItemReader<WeeklyPersonalAttendance> reader() {
        return new JdbcCursorItemReaderBuilder<WeeklyPersonalAttendance>()
                .name(STEP_NAME + "Reader")
                .fetchSize(CHUNK_SIZE)
                .dataSource(dataSource)
                .sql(sql())
                .preparedStatementSetter(ps -> ps.setString(1, jobParameter.getWeek().toString()))
                .rowMapper(rowMapper())
                .build();
    }

    @StepScope
    @Bean(name = STEP_NAME + "Writer")
    public ItemWriter<WeeklyPersonalAttendance> writer() {
        return weeklyPersonalAttendanceRepository::saveAll;
    }

    private String sql() {
        return "select  u.id           as user_id " +
                "     , u.name         as user_name " +
                "     , u.grade        as user_grade " +
                "     , u.phone_number as user_phone " +
                "     , u.sex          as user_sex " +
                "     , tm.duty        as duty " +
                "     , te.id          as term_id " +
                "     , t.id           as team_id " +
                "     , m.id           as manager_id " +
                "     , m.name         as manager_name " +
                "     , l.id           as leader_id " +
                "     , l.name         as leader_name " +
                "     , a.id           as attendance_id " +
                "     , a.week         as week " +
                "     , a.status       as status " +
                "     , a.etc          as etc " +
                "from team t " +
                "         inner join term te on te.id = t.term_id " +
                "         inner join team_member tm on tm.team_id = t.id " +
                "         inner join users u on u.id = tm.member_id " +
                "         inner join attendance a on a.team_member_id = tm.id and a.week = ? " +
                "         inner join users m on t.manager_id = m.id " +
                "         inner join users l on t.leader_id = l.id;";
    }

    private RowMapper<WeeklyPersonalAttendance> rowMapper() {
        return (rs, rowNum) -> WeeklyPersonalAttendance.builder()
                .userId(rs.getLong("user_id"))
                .userName(rs.getString("user_name"))
                .userGrade(rs.getInt("user_grade"))
                .userPhone(rs.getString("user_phone"))
                .userSex(Sex.valueOf(rs.getString("user_sex")))
                .duty(Duty.valueOf(rs.getString("duty")))
                .termId(rs.getLong("term_id"))
                .teamId(rs.getLong("team_id"))
                .managerId(rs.getLong("manager_id"))
                .managerName(rs.getString("manager_name"))
                .leaderId(rs.getLong("leader_id"))
                .leaderName(rs.getString("leader_name"))
                .attendanceId(rs.getLong("attendance_id"))
                .week(Week.previousSunday(rs.getDate("week").toLocalDate()))
                .status(AttendanceStatus.valueOf(rs.getString("status")))
                .etc(rs.getString("etc"))
                .build();
    }
}
