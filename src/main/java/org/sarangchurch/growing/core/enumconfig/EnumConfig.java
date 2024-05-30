package org.sarangchurch.growing.core.enumconfig;

import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.term.domain.team.TeamType;
import org.sarangchurch.growing.term.domain.term.TermStatus;
import org.sarangchurch.growing.training.domain.training.TrainingGroup;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("attendanceStatus", AttendanceStatus.class);

        enumMapper.put("teamType", TeamType.class);
        enumMapper.put("termStatus", TermStatus.class);
        enumMapper.put("duty", Duty.class);

        enumMapper.put("trainingGroup", TrainingGroup.class);
        enumMapper.put("trainingOption", TrainingType.class);

        enumMapper.put("sex", Sex.class);
        enumMapper.put("role", Role.class);
        return enumMapper;
    }
}
