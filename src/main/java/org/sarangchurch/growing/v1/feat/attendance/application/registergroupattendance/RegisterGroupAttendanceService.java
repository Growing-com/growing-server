package org.sarangchurch.growing.v1.feat.attendance.application.registergroupattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.infra.component.GroupAttendanceAppender;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class RegisterGroupAttendanceService {
    private final GroupAttendanceAppender groupAttendanceAppender;

    public void register(RegisterGroupAttendanceRequest request) {
        request.toLatestSunday();

        LocalDate requestDate = request.getDate();
        LocalDate latestSunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        if (requestDate.isAfter(latestSunday)) {
            throw new IllegalArgumentException("과거 날짜로만 출석체크할 수 있습니다.");
        }

        groupAttendanceAppender.append(request.toEntities());
    }
}
