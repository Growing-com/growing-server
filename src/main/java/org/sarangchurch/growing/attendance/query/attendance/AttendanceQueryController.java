package org.sarangchurch.growing.attendance.query.attendance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AttendanceQueryController {
    private final AttendanceQueryRepository attendanceQueryRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/attendance/search")
    public Page<AttendanceSearchResult> search(
            @ModelAttribute @Valid AttendanceSearchCond cond,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        cond.validateParams();

        if (cond.getName() != null || cond.getGrade() != null) {
            return attendanceQueryRepository.findByUserNamePrefixOrUserAge(cond, PageRequest.of(page, size));
        }

        if (cond.getCodyId() != null) {
            return attendanceQueryRepository.findByCody(cond, PageRequest.of(page, size));
        }

        if (cond.getIsNewOnly()) {
            return attendanceQueryRepository.findNewOnly(cond, PageRequest.of(page, size));
        }

        throw new IllegalArgumentException("지원하지 않는 검색 조건입니다.");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/api/attendance")
    public ApiResponse<List<CodyWeeklyAttendanceResult>> findByCodyAndWeek(
            @RequestParam Long codyId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate week
    ) {
        return ApiResponse.of(attendanceQueryRepository.findByCodyAndWeek(codyId, Week.previousSunday(week)));
    }
}
