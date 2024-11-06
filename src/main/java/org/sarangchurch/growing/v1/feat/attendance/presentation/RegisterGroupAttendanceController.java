package org.sarangchurch.growing.v1.feat.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.application.registergroupattendance.RegisterGroupAttendanceRequest;
import org.sarangchurch.growing.v1.feat.attendance.application.registergroupattendance.RegisterGroupAttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterGroupAttendanceController {

    private final RegisterGroupAttendanceService registerGroupAttendanceService;

    @PostMapping("/api/v1/attendances/group-attendance-check")
    public void groupAttendanceCheck(@RequestBody @Valid RegisterGroupAttendanceRequest request) {
        registerGroupAttendanceService.register(request);
    }
}
