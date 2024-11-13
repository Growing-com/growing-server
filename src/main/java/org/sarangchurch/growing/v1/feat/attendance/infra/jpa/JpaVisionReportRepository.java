package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.VisionReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaVisionReportRepository extends JpaRepository<VisionReport, Long>, VisionReportRepository {
}
