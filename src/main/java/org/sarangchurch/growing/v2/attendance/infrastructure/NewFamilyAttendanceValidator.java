package org.sarangchurch.growing.v2.attendance.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceValidator {

    public void validate(Long userId, RegisterNewFamilyAttendanceRequest request) {
        throw new IllegalStateException("TODO");
    }
}
