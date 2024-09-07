package org.sarangchurch.growing.v1.feat.attendance.application.registernewfamilyattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.infra.component.NewFamilyAttendanceAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceService {
    private final NewFamilyAttendanceAppender newFamilyAttendanceAppender;

    public void register(RegisterNewFamilyAttendanceRequest request) {
        newFamilyAttendanceAppender.append(request);
    }
}
