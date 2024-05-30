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
public class WeeklyManagerAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long weeklyAttendanceId;
    private Long managerId;
    private String managerName;
    private Long totalRegistered;
    private Long totalAttendance;

    public WeeklyManagerAttendance(
            Long managerId,
            String managerName,
            Long totalRegistered,
            Long totalAttendance
    ) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.totalRegistered = totalRegistered;
        this.totalAttendance = totalAttendance;
    }

    void setWeeklyAttendanceId(Long weeklyAttendanceId) {
        this.weeklyAttendanceId = weeklyAttendanceId;
    }

    void merge(WeeklyManagerAttendance attendance) {
        this.totalAttendance += attendance.totalAttendance;
    }
}
