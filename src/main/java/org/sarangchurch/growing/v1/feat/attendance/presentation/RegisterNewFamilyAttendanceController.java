package org.sarangchurch.growing.v1.feat.attendance.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v1.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceController {

    private final RegisterNewFamilyAttendanceService registerNewFamilyAttendanceService;

    @PostMapping("/api/v1/new-families/attendance-check")
    public void registerNewFamilyAttendance(@RequestBody @Valid RegisterNewFamilyAttendanceRequest request) {
        registerNewFamilyAttendanceService.register(request);
    }
}
