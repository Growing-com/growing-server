package org.sarangchurch.growing.v2.attendance.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceAppender {

    @Transactional
    public void append(RegisterNewFamilyAttendanceRequest request) {
        System.out.println("NewFamilyAttendanceAppender.append");
    }
}
