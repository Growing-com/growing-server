package org.sarangchurch.growing.v2.feat.attendance.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.CursorBasedPageResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryController {

    private final NewFamilyAttendanceQueryRepository newFamilyAttendanceQueryRepository;

    @GetMapping("/api/v2/new-family-attendances")
    public CursorBasedPageResponse<NewFamilyAttendance> findNewFamilyAttendance(
            @RequestParam(value = "startDate", defaultValue = "1970-01-01")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(value = "endDate", defaultValue = "2999-01-01")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(value = "cursor", defaultValue = "2999-01-01T00:00:00Z")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime cursor,

            @RequestParam(value = "limit", defaultValue = "10")
            int limit
    ) {
        List<NewFamilyAttendance> attendances =
                newFamilyAttendanceQueryRepository.findPromotedIncludedByDateRange(startDate, endDate, cursor, limit + 1);

        return CursorBasedPageResponse.of(attendances, limit);

    }
}
