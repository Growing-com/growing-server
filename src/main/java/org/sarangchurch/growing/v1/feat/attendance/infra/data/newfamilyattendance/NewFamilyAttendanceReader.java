package org.sarangchurch.growing.v1.feat.attendance.infra.data.newfamilyattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceReader {
    private final NewFamilyAttendanceRepository newFamilyAttendanceRepository;

    public long countByDate(LocalDate date) {
        return newFamilyAttendanceRepository.countByDate(date);
    }
}
