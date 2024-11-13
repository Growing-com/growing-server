package org.sarangchurch.growing.v1.feat.attendance.domain.visionreport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vision_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "total_active")
    private Integer totalActive;

    @Column(name = "total_attendance_registered")
    private Integer totalAttendanceRegistered;

    @Builder
    public VisionReport(LocalDate date, Integer totalActive, Integer totalAttendanceRegistered) {
        this.date = date;
        this.totalActive = totalActive;
        this.totalAttendanceRegistered = totalAttendanceRegistered;
    }

    public void update(long activeUserCount, long totalAttendanceRegisteredCount) {
        totalActive = (int) activeUserCount;
        totalAttendanceRegistered = (int) totalAttendanceRegisteredCount;
    }
}
