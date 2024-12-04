package org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance;

import java.time.LocalDate;
import java.util.List;

public interface NewFamilyAttendanceRepository {
    <S extends NewFamilyAttendance> List<S> saveAll(Iterable<S> entities);

    void deleteByNewFamilyIdInAndDate(List<Long> newFamilyIds, LocalDate date);

    void deleteByNewFamilyIdIn(List<Long> newFamilyIds);

    List<NewFamilyAttendance> findByDate(LocalDate date);
}
