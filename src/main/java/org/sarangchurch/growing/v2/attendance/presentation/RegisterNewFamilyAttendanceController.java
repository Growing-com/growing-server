package org.sarangchurch.growing.v2.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceController {

    private final RegisterNewFamilyAttendanceService registerNewFamilyAttendanceService;

    @PostMapping("/api/v2/new-family-attendance")
    public void registerNewFamilyAttendance(@RequestBody @Valid RegisterNewFamilyAttendanceRequest request) {
        registerNewFamilyAttendanceService.register(request);
    }
}
