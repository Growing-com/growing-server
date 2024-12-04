package org.sarangchurch.growing.v1.feat.attendance.domain.codyvisionreport;

import java.time.LocalDate;
import java.util.List;

public interface CodyVisionReportRepository {
    void deleteByDate(LocalDate date);

    <S extends CodyVisionReport> List<S> saveAll(Iterable<S> codyVisionReports);
}
