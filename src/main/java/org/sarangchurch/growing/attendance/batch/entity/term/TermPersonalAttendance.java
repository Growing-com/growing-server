package org.sarangchurch.growing.attendance.batch.entity.term;

import lombok.AccessLevel;
import lombok.Builder;
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
public class TermPersonalAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long termAttendanceId;
    private Long userId;
    private Long totalWeekPassed;
    private Long totalAttend;
    private Long totalOnline;
    private Long totalAbsent;

    @Builder
    public TermPersonalAttendance(
            Long userId,
            Long totalWeekPassed,
            Long totalAttend,
            Long totalOnline,
            Long totalAbsent
    ) {
        this.userId = userId;
        this.totalWeekPassed = totalWeekPassed;
        this.totalAttend = totalAttend;
        this.totalOnline = totalOnline;
        this.totalAbsent = totalAbsent;
    }

    void setTermAttendanceId(Long termAttendanceId) {
        this.termAttendanceId = termAttendanceId;
    }

    void merge(TermPersonalAttendance attendance) {
        this.totalAttend = attendance.getTotalAttend();
        this.totalOnline = attendance.getTotalOnline();
        this.totalAbsent = attendance.getTotalAbsent();
    }
}
