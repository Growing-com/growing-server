package org.sarangchurch.growing.attendance.batch.entity.week;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyAttendanceEtc {
    private Long totalRegistered;
    private Long totalAttendance;
    private Long totalOnline;
    private Long totalAbsent;
    private Long maleRegistered;
    private Long maleAttendance;
    private Long femaleRegistered;
    private Long femaleAttendance;
    private Long newComerRegistered;
    private Long newComerAttendance;
    private Long newVisited;

    @Builder
    public WeeklyAttendanceEtc(
            Long totalRegistered,
            Long totalAttendance,
            Long totalOnline,
            Long totalAbsent,
            Long maleRegistered,
            Long maleAttendance,
            Long femaleRegistered,
            Long femaleAttendance,
            Long newComerRegistered,
            Long newComerAttendance,
            Long newVisited
    ) {
        this.totalRegistered = totalRegistered;
        this.totalAttendance = totalAttendance;
        this.totalOnline = totalOnline;
        this.totalAbsent = totalAbsent;
        this.maleRegistered = maleRegistered;
        this.maleAttendance = maleAttendance;
        this.femaleRegistered = femaleRegistered;
        this.femaleAttendance = femaleAttendance;
        this.newComerRegistered = newComerRegistered;
        this.newComerAttendance = newComerAttendance;
        this.newVisited = newVisited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyAttendanceEtc that = (WeeklyAttendanceEtc) o;
        return Objects.equals(totalRegistered, that.totalRegistered) && Objects.equals(totalAttendance, that.totalAttendance) && Objects.equals(totalOnline, that.totalOnline) && Objects.equals(totalAbsent, that.totalAbsent) && Objects.equals(maleRegistered, that.maleRegistered) && Objects.equals(maleAttendance, that.maleAttendance) && Objects.equals(femaleRegistered, that.femaleRegistered) && Objects.equals(femaleAttendance, that.femaleAttendance) && Objects.equals(newComerRegistered, that.newComerRegistered) && Objects.equals(newComerAttendance, that.newComerAttendance) && Objects.equals(newVisited, that.newVisited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalRegistered, totalAttendance, totalOnline, totalAbsent, maleRegistered, maleAttendance, femaleRegistered, femaleAttendance, newComerRegistered, newComerAttendance, newVisited);
    }
}
