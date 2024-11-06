package org.sarangchurch.growing.v1.feat.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.application.registerstumpattendance.RegisterStumpAttendanceRequest;
import org.sarangchurch.growing.v1.feat.attendance.application.registerstumpattendance.RegisterStumpAttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterStumpAttendanceController {

    private final RegisterStumpAttendanceService registerStumpAttendanceService;

    @PostMapping("/api/v1/attendances/stump-attendance-check")
    public void stumpAttendanceCheck(@RequestBody @Valid RegisterStumpAttendanceRequest request) {
        registerStumpAttendanceService.register(request);
    }
}
