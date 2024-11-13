package org.sarangchurch.growing.v1.feat.attendance.infra.data.attendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AttendanceReader {
    private final AttendanceRepository attendanceRepository;

    public long countByDate(LocalDate date) {
        return attendanceRepository.countByDate(date);
    }
}
