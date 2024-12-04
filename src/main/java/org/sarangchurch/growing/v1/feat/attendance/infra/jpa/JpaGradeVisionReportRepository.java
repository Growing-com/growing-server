package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport.GradeVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport.GradeVisionReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGradeVisionReportRepository extends JpaRepository<GradeVisionReport, Long>, GradeVisionReportRepository {
}
