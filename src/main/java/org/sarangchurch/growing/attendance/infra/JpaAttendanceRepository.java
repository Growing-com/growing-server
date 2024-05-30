package org.sarangchurch.growing.attendance.infra;

import org.sarangchurch.growing.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaAttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepository {
    @Override
    @Modifying
    @Query("DELETE FROM Attendance a where a.teamMemberId in :teamMemberIds and a.week = :week")
    void deleteByTeamMemberIdInAndWeek(List<Long> teamMemberIds, Week week);
}
