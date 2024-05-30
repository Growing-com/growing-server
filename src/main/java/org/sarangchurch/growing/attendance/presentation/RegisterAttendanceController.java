package org.sarangchurch.growing.attendance.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.application.dto.RegisterAttendanceRequest;
import org.sarangchurch.growing.attendance.application.facade.RegisterAttendanceFacade;
import org.sarangchurch.growing.auth.security.UserDetailsImpl;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterAttendanceController {

    private final RegisterAttendanceFacade registerAttendanceFacade;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/api/attendance")
    public void registerAttendance(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody @Valid RegisterAttendanceRequest request
    ) {
        if (!isValidWeek(request)) {
            throw new IllegalArgumentException("2023년 9월부터 2024년 3월까지 출석 데이터를 입력할 수 있습니다.");
        }

        registerAttendanceFacade.registerAttendance(user.getId(), request);
    }

    private  boolean isValidWeek(RegisterAttendanceRequest request) {
        Week week = Week.previousSunday(request.getWeek());

        return week.getWeek().isAfter(LocalDate.of(2023, 9, 1)) &&
                week.getWeek().isBefore(LocalDate.of(2024, 3, 1));
    }
}
