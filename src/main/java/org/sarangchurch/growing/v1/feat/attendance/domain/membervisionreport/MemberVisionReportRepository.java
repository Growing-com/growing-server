package org.sarangchurch.growing.v1.feat.attendance.domain.membervisionreport;

import java.time.LocalDate;
import java.util.List;

public interface MemberVisionReportRepository {
    <S extends MemberVisionReport> List<S> saveAll(Iterable<S> entities);

    void deleteByDate(LocalDate thisWeek);
}
