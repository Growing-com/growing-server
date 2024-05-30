package org.sarangchurch.growing.attendance.domain.attendance;

import org.sarangchurch.growing.core.types.Week;

import java.util.List;

public interface AttendanceRepository {
    <S extends Attendance> List<S> saveAll(Iterable<S> entities);
    void deleteByTeamMemberIdInAndWeek(List<Long> teamMemberIds, Week week);
    List<Attendance> findByTeamMemberId(Long teamMemberId);
    void deleteByTeamMemberId(Long teamMemberId);
}
