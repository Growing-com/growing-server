package org.sarangchurch.growing.v2.feat.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceService;
import org.sarangchurch.growing.v2.feat.auth.domain.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceController {

    private final RegisterNewFamilyAttendanceService registerNewFamilyAttendanceService;

    // TODO: v1으로 옮기기
    @PostMapping("/api/v1/new-families/attendance-check")
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
