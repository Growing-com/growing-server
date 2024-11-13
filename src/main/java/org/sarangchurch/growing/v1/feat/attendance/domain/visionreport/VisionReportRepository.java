package org.sarangchurch.growing.v1.feat.attendance.domain.visionreport;

import java.time.LocalDate;
import java.util.Optional;

public interface VisionReportRepository {
    Optional<VisionReport> findByDate(LocalDate date);

    VisionReport save(VisionReport visionReport);
}
