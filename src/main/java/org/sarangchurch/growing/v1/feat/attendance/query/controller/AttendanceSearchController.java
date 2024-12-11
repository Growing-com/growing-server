package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.query.model.AttendanceSearchListItem;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.AttendanceSearchQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceSearchController {
    private final AttendanceSearchQueryRepository attendanceSearchQueryRepository;

    @GetMapping("/api/v1/attendances/search")
    public ApiResponse<List<AttendanceSearchListItem>> searchAttendances(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ApiResponse.of(attendanceSearchQueryRepository.search(startDate, endDate));
    }
}
