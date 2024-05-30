package org.sarangchurch.growing.attendance.application.facade;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.application.dto.RegisterAttendanceRequest;
import org.sarangchurch.growing.attendance.application.service.AttendanceService;
import org.sarangchurch.growing.attendance.application.service.TeamAccessValidationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class RegisterAttendanceFacade {
    private final TeamAccessValidationService teamAccessValidationService;
    private final AttendanceService attendanceService;

    public void registerAttendance(Long userId, RegisterAttendanceRequest request) {
        teamAccessValidationService.validateHasAccess(userId, request);
        attendanceService.create(userId, request);
    }
}
