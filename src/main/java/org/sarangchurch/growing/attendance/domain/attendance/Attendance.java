package org.sarangchurch.growing.attendance.domain.attendance;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uq_attendance_team_member_id_and_week", columnNames = {"teamMemberId", "week"})
})
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teamMemberId;
    @Embedded
    private Week week;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private String etc;

    @Builder
    public Attendance(Long teamMemberId, Week week, AttendanceStatus status, String etc) {
        this.teamMemberId = teamMemberId;
        this.week = week;
        this.status = status;
        this.etc = etc;
    }
}
