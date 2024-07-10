package org.sarangchurch.growing.v2.feat.attendance.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class NewFamilyAttendanceQueryController {

    private final NewFamilyAttendanceQueryRepository newFamilyAttendanceQueryRepository;

    @GetMapping("/api/v2/new-family-attendances")
    public Page<NewFamilyAttendance> findNewFamilyAttendance(
            @RequestParam(value = "startDate", defaultValue = "1970-01-01")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(value = "endDate", defaultValue = "2999-01-01")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(value = "page", defaultValue = "0")
            int page,

            @RequestParam(value = "size", defaultValue = "10")
            int size
    ) {
        return newFamilyAttendanceQueryRepository.findPromotedIncludedByDateRange(
                startDate, endDate, PageRequest.of(page, size));
    }
}
