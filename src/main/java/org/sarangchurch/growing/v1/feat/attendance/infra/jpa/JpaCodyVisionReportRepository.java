package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport.CodyVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport.CodyVisionReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCodyVisionReportRepository extends JpaRepository<CodyVisionReport, Long>, CodyVisionReportRepository {
}
