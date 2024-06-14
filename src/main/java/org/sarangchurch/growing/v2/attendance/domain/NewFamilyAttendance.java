package org.sarangchurch.growing.v2.attendance.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

// TODO: UNIQUE(newFamilyId, date)
@Entity
@Table(name = "new_family_attendance")
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
}
