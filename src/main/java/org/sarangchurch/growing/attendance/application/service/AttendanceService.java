package org.sarangchurch.growing.attendance.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.attendance.application.dto.RegisterAttendanceRequest;
import org.sarangchurch.growing.attendance.domain.event.AttendanceRegisteredEvent;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceRepository;
import org.sarangchurch.growing.core.types.Events;
import org.sarangchurch.growing.core.types.Week;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public void create(Long userId, RegisterAttendanceRequest request) {
        Week prevWeek = Week.previousSunday(request.getWeek());

        attendanceRepository.deleteByTeamMemberIdInAndWeek(request.teamMemberIds(), prevWeek);
        attendanceRepository.saveAll(request.toEntities());

        log.info("[RegisterAttendance] userId: {}, week: {}, totalAttendanceCount: {}",
                userId, request.getWeek(), request.getAttendances().size());

        Events.raise(new AttendanceRegisteredEvent(prevWeek));
    }
}
