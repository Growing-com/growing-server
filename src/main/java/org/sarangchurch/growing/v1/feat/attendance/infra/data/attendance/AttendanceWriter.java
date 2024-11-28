package org.sarangchurch.growing.v1.feat.attendance.infra.data.attendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.AttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttendanceWriter {
    private final AttendanceRepository attendanceRepository;

    public void deleteByUserIdInAndDate(List<Long> userIds, LocalDate attendanceDate) {
        attendanceRepository.deleteByUserIdInAndDate(userIds, attendanceDate);
    }

    public void saveAll(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
    }
}
