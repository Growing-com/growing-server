package org.sarangchurch.growing.v2.attendance.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.infrastructure.NewFamilyAttendanceAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceService {
    private final NewFamilyAttendanceAppender newFamilyAttendanceAppender;

    public void register(RegisterNewFamilyAttendanceRequest request) {
        newFamilyAttendanceAppender.append(request);
    }
}
