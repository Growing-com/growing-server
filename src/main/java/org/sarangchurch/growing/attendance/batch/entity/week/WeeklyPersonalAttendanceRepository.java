package org.sarangchurch.growing.attendance.batch.entity.week;

import org.sarangchurch.growing.core.types.Week;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyPersonalAttendanceRepository extends JpaRepository<WeeklyPersonalAttendance, Long> {
    List<WeeklyPersonalAttendance> findByWeek(Week week);
    void deleteByWeek(Week week);
}
