package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.query.model.NormalAttendanceListItem;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.NormalAttendanceQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NormalAttendanceQueryController {
    private final NormalAttendanceQueryRepository normalAttendanceQueryRepository;

    @GetMapping("/api/v1/attendances/normal-attendance")
    public ApiResponse<List<NormalAttendanceListItem>> findNormalAttendance(
            @RequestParam Long codyId,
            @RequestParam LocalDate date
    ) {
        return ApiResponse.of(normalAttendanceQueryRepository.findByCodyAndDate(codyId, date));
    }
}
