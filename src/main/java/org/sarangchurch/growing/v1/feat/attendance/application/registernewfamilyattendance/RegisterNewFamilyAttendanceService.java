package org.sarangchurch.growing.v1.feat.attendance.application.registernewfamilyattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.infra.component.NewFamilyAttendanceAppender;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegisterNewFamilyAttendanceService {
    private final NewFamilyAttendanceAppender newFamilyAttendanceAppender;

    public void register(RegisterNewFamilyAttendanceRequest request) {
        LocalDate date = request.getDate();

        if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("일요일 날짜로만 출석체크할 수 있습니다.");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로만 출석체크할 수 있습니다.");
        }

        newFamilyAttendanceAppender.append(request);
    }
}
