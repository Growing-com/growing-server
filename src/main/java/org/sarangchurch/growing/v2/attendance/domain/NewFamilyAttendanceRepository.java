package org.sarangchurch.growing.v2.attendance.domain;

import java.time.LocalDate;
import java.util.List;

public interface NewFamilyAttendanceRepository {
    <S extends NewFamilyAttendance> List<S> saveAll(Iterable<S> entities);

    void deleteByNewFamilyIdInAndDate(List<Long> newFamilyIds, LocalDate date);
}
