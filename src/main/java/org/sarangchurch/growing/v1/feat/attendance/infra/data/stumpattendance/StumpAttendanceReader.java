package org.sarangchurch.growing.v1.feat.attendance.infra.data.stumpattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StumpAttendanceReader {
    private final StumpAttendanceRepository stumpAttendanceRepository;

    public long countByDate(LocalDate date) {
        return stumpAttendanceRepository.countByDate(date);
    }
}
