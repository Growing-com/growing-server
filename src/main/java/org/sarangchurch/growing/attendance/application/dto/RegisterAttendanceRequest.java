package org.sarangchurch.growing.attendance.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RegisterAttendanceRequest {

    @NotNull
    private LocalDate week;

    @NotEmpty
    @Valid
    private List<AttendanceRequest> attendances;

    public RegisterAttendanceRequest(LocalDate week, List<AttendanceRequest> attendances) {
        this.week = week;
        this.attendances = attendances;
    }

    public List<Attendance> toEntities() {
        return this.attendances
                .stream()
                .map(it -> Attendance.builder()
                        .teamMemberId(it.teamMemberId)
                        .week(Week.previousSunday(week))
                        .status(it.status)
                        .etc(it.etc)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<Long> teamIds() {
        return this.attendances.stream()
                .map(it -> it.teamId)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Long> teamMemberIds() {
        return this.attendances.stream()
                .map(it -> it.teamMemberId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Getter
    @NoArgsConstructor
    public static class AttendanceRequest {
        @NotNull
        private Long teamMemberId;
        @NotNull
        private AttendanceStatus status;
        @NotNull
        private Long teamId;
        private String etc;

        public AttendanceRequest(Long teamMemberId, AttendanceStatus status, Long teamId, String etc) {
            this.teamMemberId = teamMemberId;
            this.status = status;
            this.teamId = teamId;
            this.etc = etc;
        }
    }
}
