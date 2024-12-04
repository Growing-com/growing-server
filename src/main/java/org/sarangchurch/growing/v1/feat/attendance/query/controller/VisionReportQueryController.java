package org.sarangchurch.growing.v1.feat.attendance.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.attendance.batch.Sunday;
import org.sarangchurch.growing.v1.feat.attendance.query.model.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.query.repository.VisionReportQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class VisionReportQueryController {
    private final VisionReportQueryRepository visionReportQueryRepository;

    @GetMapping("/api/v1/vision-report")
    public ApiResponse<VisionReport> getByDate(@RequestParam LocalDate date) {
        Sunday previousSunday = Sunday.previousSunday(date);

        return ApiResponse.of(visionReportQueryRepository.getByDate(previousSunday.getDate()));
    }
}
