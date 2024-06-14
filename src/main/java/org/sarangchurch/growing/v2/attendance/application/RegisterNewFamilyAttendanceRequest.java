package org.sarangchurch.growing.v2.attendance.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterNewFamilyAttendanceRequest {
    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate date;

    @NotEmpty(message = "출석 리스트를 입력해주세요.")
    @Valid
    private List<NewFamilyAttendanceItems> attendanceItems;

    @Getter
    @NoArgsConstructor
    public static class NewFamilyAttendanceItems {
        @NotNull(message = "새가족 id를 입력해주세요.")
        private Long newFamilyId;
        @NotNull(message = "출석 정보를 입력해주세요.")
        private AttendanceStatus status;
        private String reason;
    }
}
