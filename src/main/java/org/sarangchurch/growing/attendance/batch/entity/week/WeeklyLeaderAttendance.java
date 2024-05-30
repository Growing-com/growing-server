package org.sarangchurch.growing.attendance.batch.entity.week;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyLeaderAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long weeklyAttendanceId;
    private Long managerId;
    private String managerName;
    private Long leaderId;
    private String leaderName;
    private String leaderPhone;
    private Long totalRegistered;
    private Long totalAttendance;

    public WeeklyLeaderAttendance(
            Long managerId,
            String managerName,
            Long leaderId,
            String leaderName,
            String leaderPhone,
            Long totalRegistered,
            Long totalAttendance
    ) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.leaderPhone = leaderPhone;
        this.totalRegistered = totalRegistered;
        this.totalAttendance = totalAttendance;
    }

    void setWeeklyAttendanceId(Long weeklyAttendanceId) {
        this.weeklyAttendanceId = weeklyAttendanceId;
    }

    void merge(WeeklyLeaderAttendance attendance) {
        this.totalAttendance += attendance.totalAttendance;
    }
}
