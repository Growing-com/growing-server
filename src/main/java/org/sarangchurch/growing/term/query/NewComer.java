package org.sarangchurch.growing.term.query;

import lombok.Getter;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NewComer {
    private final Long userId;
    private final Long teamId;
    private final Long teamMemberId;
    private final String name;
    private final Integer grade;
    private final Sex sex;
    private final Duty duty;
    private final Role role;
    private final String phoneNumber;
    private final LocalDate birth;
    private final LocalDate visitDate;
    private final LocalDate lineupDate;
    private final LocalDate lineoutDate;
    private final String newTeamLeaderName;
    private final String firstPlantManagerName;
    private final String firstPlantLeaderName;
    private final Boolean isActive;
    private final String etc;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    public NewComer(
            Long userId,
            Long teamId,
            Long teamMemberId,
            String name,
            Integer grade,
            Sex sex,
            Duty duty,
            Role role,
            String phoneNumber,
            LocalDate birth,
            LocalDate visitDate,
            LocalDate lineupDate,
            LocalDate lineoutDate,
            String newTeamLeaderName,
            String firstPlantManagerName,
            String firstPlantLeaderName,
            Boolean isActive,
            String etc,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.userId = userId;
        this.teamId = teamId;
        this.teamMemberId = teamMemberId;
        this.name = name;
        this.grade = grade;
        this.sex = sex;
        this.duty = duty;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.visitDate = visitDate;
        this.lineupDate = lineupDate == null || lineupDate.equals(LocalDate.of(1970, 1, 1))
                ? null
                : lineupDate;
        this.lineoutDate = lineoutDate == null || lineoutDate.equals(LocalDate.of(1970, 1, 1))
                ? null
                : lineoutDate;
        this.newTeamLeaderName = newTeamLeaderName;
        this.firstPlantManagerName = newTeamLeaderName.equals(firstPlantManagerName)
                ? null
                : firstPlantManagerName;
        this.firstPlantLeaderName = newTeamLeaderName.equals(firstPlantLeaderName)
                ? null
                : firstPlantLeaderName;
        this.isActive = isActive;
        this.etc = etc;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}
