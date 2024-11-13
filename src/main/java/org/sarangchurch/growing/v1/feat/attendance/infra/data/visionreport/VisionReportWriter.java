package org.sarangchurch.growing.v1.feat.attendance.infra.data.visionreport;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReportRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VisionReportWriter {
    private final VisionReportRepository visionReportRepository;

    public VisionReport save(VisionReport visionReport) {
        return visionReportRepository.save(visionReport);
    }
}
