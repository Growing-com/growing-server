package org.sarangchurch.growing.v2.attendance.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.infrastructure.NewFamilyAttendanceAppender;
import org.sarangchurch.growing.v2.attendance.infrastructure.NewFamilyAttendanceValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceService {
    private final NewFamilyAttendanceValidator newFamilyAttendanceValidator;
    private final NewFamilyAttendanceAppender newFamilyAttendanceAppender;

    public void register(Long userId, RegisterNewFamilyAttendanceRequest request) {
        newFamilyAttendanceValidator.validate(userId, request);
        newFamilyAttendanceAppender.append(request);
    }

    public void register(RegisterNewFamilyAttendanceRequest request) {
        newFamilyAttendanceAppender.append(request);
    }
}
