package org.sarangchurch.growing.attendance.batch.entity.week;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Week week;
    @Embedded
    private WeeklyManagerAttendances managerAttendances = new WeeklyManagerAttendances();
    @Embedded
    private WeeklyLeaderAttendances leaderAttendances = new WeeklyLeaderAttendances();
    @Embedded
    private WeeklyGradeAttendances gradeAttendances = new WeeklyGradeAttendances();
    @Embedded
    private WeeklyAttendanceEtc attendanceEtc;

    public WeeklyAttendance(Week week) {
        this.week = week;
        this.attendanceEtc = WeeklyAttendanceEtc.builder()
                .totalRegistered(0L)
                .totalAttendance(0L)
                .totalOnline(0L)
                .totalAbsent(0L)
                .maleRegistered(0L)
                .maleAttendance(0L)
                .femaleRegistered(0L)
                .femaleAttendance(0L)
                .newComerRegistered(0L)
                .newComerAttendance(0L)
                .newVisited(0L)
                .build();
    }

    public void mergeManager(List<? extends WeeklyManagerAttendance> added) {
        added.forEach(it -> it.setWeeklyAttendanceId(id));
        managerAttendances.merge(added);
    }

    public void mergeLeader(List<? extends WeeklyLeaderAttendance> added) {
        added.forEach(it -> it.setWeeklyAttendanceId(id));
        leaderAttendances.merge(added);
    }

    public void mergeGrade(List<? extends WeeklyGradeAttendance> added) {
        added.forEach(it -> it.setWeeklyAttendanceId(id));
        gradeAttendances.merge(added);
    }

    public void setAttendanceEtc(WeeklyAttendanceEtc attendanceEtc) {
        this.attendanceEtc = attendanceEtc;
    }
}
