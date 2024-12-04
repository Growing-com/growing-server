package org.sarangchurch.growing.v1.feat.attendance.domain.attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository {
    <S extends Attendance> List<S> saveAll(Iterable<S> entities);
    void deleteByUserIdInAndDate(List<Long> userIds, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
    long countByCodyId(Long codyId);
}
