package org.sarangchurch.growing.v1.feat.attendance.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StumpAttendanceWriter {
    private final StumpAttendanceRepository stumpAttendanceRepository;

    public void deleteByUserIdInAndDate(List<Long> userIds, LocalDate attendanceDate) {
        stumpAttendanceRepository.deleteByUserIdInAndDate(userIds, attendanceDate);
    }

    public void saveAll(List<StumpAttendance> attendances) {
        stumpAttendanceRepository.saveAll(attendances);
    }
}
