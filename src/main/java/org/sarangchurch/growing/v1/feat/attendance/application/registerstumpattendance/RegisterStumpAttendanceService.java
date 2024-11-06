package org.sarangchurch.growing.v1.feat.attendance.application.registerstumpattendance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class RegisterStumpAttendanceService {

    public void register(RegisterStumpAttendanceRequest request) {
        request.toLatestSunday();

        LocalDate requestDate = request.getDate();
        LocalDate latestSunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        if (requestDate.isAfter(latestSunday)) {
            throw new IllegalArgumentException("과거 날짜로만 출석체크할 수 있습니다.");
        }
    }
}
