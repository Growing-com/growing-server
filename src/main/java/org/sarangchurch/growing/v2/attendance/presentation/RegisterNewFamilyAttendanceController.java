package org.sarangchurch.growing.v2.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceService;
import org.sarangchurch.growing.v2.auth.domain.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceController {

    private final RegisterNewFamilyAttendanceService registerNewFamilyAttendanceService;

    @PostMapping("/api/v2/new-family-attendance")
    public void registerNewFamilyAttendance(
            @AuthenticationPrincipal Principal principal,
            @RequestBody @Valid RegisterNewFamilyAttendanceRequest request
    ) {
        if (principal.isSuperUser()) {
            registerNewFamilyAttendanceService.register(request);

            return;
        }

        registerNewFamilyAttendanceService.register(principal.getUserId(), request);
    }
}
