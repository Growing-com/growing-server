package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepository {
}
