package org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

// TODO: UNIQUE(newFamilyId, date)
@Entity
@Table(name = "new_family_attendance", uniqueConstraints = {
        @UniqueConstraint(name = "new_family_attendance_unique_new_family_id_date", columnNames = {"new_family_id", "date"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "new_family_id", nullable = false)
    private Long newFamilyId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "reason")
    private String reason;

    @Builder
    public NewFamilyAttendance(Long newFamilyId, LocalDate date, AttendanceStatus status, String reason) {
        this.newFamilyId = newFamilyId;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }
}
