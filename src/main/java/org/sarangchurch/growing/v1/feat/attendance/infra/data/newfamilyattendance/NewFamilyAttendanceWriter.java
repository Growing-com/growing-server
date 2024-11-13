package org.sarangchurch.growing.v1.feat.attendance.infra.data.newfamilyattendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceWriter {
    private final NewFamilyAttendanceRepository newFamilyAttendanceRepository;

    public void deleteByNewFamilyIdInAndDate(List<Long> newFamilyIds, LocalDate date) {
        newFamilyAttendanceRepository.deleteByNewFamilyIdInAndDate(newFamilyIds, date);
    }

    public void saveAll(List<NewFamilyAttendance> attendances) {
        newFamilyAttendanceRepository.saveAll(attendances);
    }

    public void deleteByNewFamilyIdIn(List<Long> newFamilyIds) {
        newFamilyAttendanceRepository.deleteByNewFamilyIdIn(newFamilyIds);
    }
}
