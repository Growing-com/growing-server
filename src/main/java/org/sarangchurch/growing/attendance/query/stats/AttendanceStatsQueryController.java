package org.sarangchurch.growing.attendance.query.stats;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AttendanceStatsQueryController {
    private final AttendanceStatsQueryRepository attendanceStatsQueryRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/attendance")
    public ApiResponse<List<WeeklyPersonalAttendanceSummaryResult>> findAbsentOrNewByDateBetween(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) Boolean isAbsent,
            @RequestParam(required = false) Boolean isNewOnly
    ) {
        if (isAbsent != null && isAbsent) {
            return ApiResponse.of(attendanceStatsQueryRepository.findAbsentByDateBetween(startDate, endDate));
        }

        if (isNewOnly != null && isNewOnly) {
            return ApiResponse.of(attendanceStatsQueryRepository.findNewOnlyByDateBetween(startDate, endDate));
        }

        throw new IllegalArgumentException("지원하지 않는 검색 조건입니다.");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/term/{termId}/attendanceProgress")
    public ApiResponse<AttendanceProgressResult> getAttendanceProgressByWeek(
            @PathVariable Long termId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate week
    ) {
        return ApiResponse.of(attendanceStatsQueryRepository.getAttendanceProgressByTermAndWeek(termId, Week.previousSunday(week)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/statistics/attendanceSummary")
    public ApiResponse<List<WeeklyAttendanceSummaryResult>> findSummaryByWeek(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        return ApiResponse.of(attendanceStatsQueryRepository.findSummaryByWeek(startDate, endDate));
    }
}
