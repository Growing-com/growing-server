package org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance;

import java.time.LocalDate;
import java.util.List;

public interface StumpAttendanceRepository {
    <S extends StumpAttendance> List<S> saveAll(Iterable<S> entities);

    void deleteByUserIdInAndDate(List<Long> userIds, LocalDate date);

    long countByDate(LocalDate date);
}
