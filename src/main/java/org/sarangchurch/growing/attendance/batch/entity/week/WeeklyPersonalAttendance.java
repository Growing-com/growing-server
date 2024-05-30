package org.sarangchurch.growing.attendance.batch.entity.week;

import lombok.*;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.core.types.BaseEntity;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Sex;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyPersonalAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userName;
    private Integer userGrade;
    private String userPhone;
    @Enumerated(EnumType.STRING)
    private Sex userSex;
    @Enumerated(EnumType.STRING)
    private Duty duty;
    private Long termId;
    private Long teamId;
    private Long managerId;
    private String managerName;
    private Long leaderId;
    private String leaderName;
    private Long attendanceId;
    @Embedded
    private Week week;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private String etc;

    @Builder
    public WeeklyPersonalAttendance(
            Long userId,
            String userName,
            Integer userGrade,
            String userPhone,
            Sex userSex,
            Duty duty,
            Long termId,
            Long teamId,
            Long managerId,
            String managerName,
            Long leaderId,
            String leaderName,
            Long attendanceId,
            Week week,
            AttendanceStatus status,
            String etc
    ) {
        this.userId = userId;
        this.userName = userName;
        this.userGrade = userGrade;
        this.userPhone = userPhone;
        this.userSex = userSex;
        this.duty = duty;
        this.termId = termId;
        this.teamId = teamId;
        this.managerId = managerId;
        this.managerName = managerName;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.attendanceId = attendanceId;
        this.week = week;
        this.status = status;
        this.etc = etc;
    }

    public boolean isAttendOrOnline() {
        return isAttend() || isOnline();
    }

    public boolean isAttend() {
        return status == AttendanceStatus.ATTEND;
    }

    public boolean isOnline() {
        return status == AttendanceStatus.ONLINE;
    }

    public boolean isAbsent() {
        return status == AttendanceStatus.ABSENT;
    }

    public boolean isMale() {
        return userSex == Sex.MALE;
    }

    public boolean isFemale() {
        return userSex == Sex.FEMALE;
    }
}
