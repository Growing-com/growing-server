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
public class WeeklyGradeAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long weeklyAttendanceId;
    private Integer grade;
    private Long totalRegistered;
    private Long totalAttendance;

    public WeeklyGradeAttendance(Integer grade, Long totalRegistered, Long totalAttendance) {
        this.grade = grade;
        this.totalRegistered = totalRegistered;
        this.totalAttendance = totalAttendance;
    }

    void setWeeklyAttendanceId(Long weeklyAttendanceId) {
        this.weeklyAttendanceId = weeklyAttendanceId;
    }

    void merge(WeeklyGradeAttendance attendance) {
        this.totalAttendance += attendance.totalAttendance;
    }
}
