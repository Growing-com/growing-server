package org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReportRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VisionReportReader {
    private final VisionReportRepository visionReportRepository;

    public Optional<VisionReport> findByDate(LocalDate date) {
        return visionReportRepository.findByDate(date);
    }
}
