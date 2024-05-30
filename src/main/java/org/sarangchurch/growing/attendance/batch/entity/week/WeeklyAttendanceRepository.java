package org.sarangchurch.growing.attendance.batch.entity.week;

import org.sarangchurch.growing.core.types.Week;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WeeklyAttendanceRepository extends JpaRepository<WeeklyAttendance, Long> {
    Optional<WeeklyAttendance> findByWeek(Week week);

    @Query("select new org.sarangchurch.growing.attendance.batch.entity.week.WeeklyManagerAttendance(pa.managerId, pa.managerName, COUNT(*), COUNT(case when pa.status = 'ATTEND' then 1 end)) " +
            "from WeeklyPersonalAttendance pa " +
            "where pa.week = :week " +
            "group by pa.managerId " +
            "order by pa.id")
    Page<WeeklyManagerAttendance> findManagerGroupedByWeek(Week week, Pageable pageable);

    @Query("select new org.sarangchurch.growing.attendance.batch.entity.week.WeeklyLeaderAttendance(pa.managerId, pa.managerName, pa.leaderId, pa.leaderName, l.phoneNumber, COUNT(*), COUNT(case when pa.status = 'ATTEND' then 1 end)) " +
            "from WeeklyPersonalAttendance pa " +
            "join UserEntity l on l.id = pa.leaderId " +
            "where pa.week = :week " +
            "group by pa.leaderId " +
            "order by pa.id")
    Page<WeeklyLeaderAttendance> findLeaderGroupedByWeek(Week week, Pageable pageable);

    @Query("select new org.sarangchurch.growing.attendance.batch.entity.week.WeeklyGradeAttendance(pa.userGrade, COUNT(*), COUNT(case when pa.status = 'ATTEND' then 1 end)) " +
            "from WeeklyPersonalAttendance pa " +
            "where pa.week = :week " +
            "group by pa.userGrade")
    Page<WeeklyGradeAttendance> findGradeGroupedByWeek(Week week, Pageable pageable);

    void deleteByWeek(Week week);
}
