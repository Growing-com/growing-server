package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStumpAttendanceRepository extends JpaRepository<StumpAttendance, Long>, StumpAttendanceRepository {
}
