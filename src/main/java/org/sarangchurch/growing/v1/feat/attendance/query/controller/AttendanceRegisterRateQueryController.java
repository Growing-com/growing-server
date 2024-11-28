package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.query.model.AttendanceRegisterRate;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.AttendanceRegisterRateQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AttendanceRegisterRateQueryController {
    private final AttendanceRegisterRateQueryRepository attendanceRegisterRateQueryRepository;

    @GetMapping("/api/v1/attendances/attendance-register-rate")
    public ApiResponse<AttendanceRegisterRate> getAttendanceRegisterRate(@RequestParam LocalDate date) {
        Sunday previousSunday = Sunday.previousSunday(date);

        return ApiResponse.of(attendanceRegisterRateQueryRepository.getByDate(previousSunday.getDate()));
    }
}
