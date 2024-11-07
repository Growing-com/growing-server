package org.sarangchurch.growing.v1.feat.attendance.domain.attendance;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "reason")
    private String reason;

    @Builder
    public Attendance(Long userId, Long smallGroupId, Long newFamilyGroupId, LocalDate date, AttendanceStatus status, String reason) {
        this.userId = userId;
        this.smallGroupId = smallGroupId;
        this.newFamilyGroupId = newFamilyGroupId;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public void setCodyId(Long codyId) {
        this.codyId = codyId;
    }

    public boolean isSmallGroupAttendance() {
        return smallGroupId != null && newFamilyGroupId == null;
    }

    public boolean isNewFamilyGroupAttendance() {
        return smallGroupId == null && newFamilyGroupId != null;
    }
}