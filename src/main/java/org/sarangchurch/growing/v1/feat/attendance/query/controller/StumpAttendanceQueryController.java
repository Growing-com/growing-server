package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.query.model.StumpAttendanceListItem;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.StumpAttendanceQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StumpAttendanceQueryController {
    private final StumpAttendanceQueryRepository stumpAttendanceQueryRepository;

    @GetMapping("/api/v1/attendances/stump-attendance")
    public ApiResponse<List<StumpAttendanceListItem>> findStumpAttendance(@RequestParam LocalDate date) {
        return ApiResponse.of(stumpAttendanceQueryRepository.findByDate(date));
    }
}
