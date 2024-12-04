package org.sarangchurch.growing.v1.feat.attendance.domain.gradevisionreport;

import java.time.LocalDate;
import java.util.List;

public interface GradeVisionReportRepository {
    void deleteByDate(LocalDate date);

    <S extends GradeVisionReport> List<S> saveAll(Iterable<S> entities);
}
