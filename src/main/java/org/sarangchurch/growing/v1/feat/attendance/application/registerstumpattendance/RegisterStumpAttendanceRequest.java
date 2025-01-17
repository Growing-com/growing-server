package org.sarangchurch.growing.v1.feat.attendance.application.registerstumpattendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RegisterStumpAttendanceRequest {
    @NotNull(message = "텀 id를 입력해주세요.")
    private Long termId;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate date;

    @NotEmpty(message = "출석 리스트를 입력해주세요.")
    @Valid
    private List<StumpAttendanceItems> attendanceItems;

    public void toLatestSunday() {
        date = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    public List<StumpAttendance> toEntities() {
        return attendanceItems.stream()
                .map(it -> StumpAttendance.builder()
                        .userId(it.userId)
                        .termId(termId)
                        .date(date)
                        .status(it.status)
                        .reason(it.reason)
                        .build()
                ).collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class StumpAttendanceItems {
        @NotNull(message = "지체 id를 입력해주세요.")
        private Long userId;

        @NotNull(message = "출석 정보를 입력해주세요.")
        private AttendanceStatus status;

        private String reason;
    }
}
