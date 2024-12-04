package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport.MemberVisionReport;
import org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport.MemberVisionReportRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberVisionReportRepository extends JpaRepository<MemberVisionReport, Long>, MemberVisionReportRepository {
}
