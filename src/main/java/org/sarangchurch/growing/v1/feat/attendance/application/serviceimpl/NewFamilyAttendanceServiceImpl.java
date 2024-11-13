package org.sarangchurch.growing.v1.feat.attendance.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.attendance.NewFamilyAttendanceService;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.newfamilyattendance.NewFamilyAttendanceWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyAttendanceServiceImpl implements NewFamilyAttendanceService {
    private final NewFamilyAttendanceWriter newFamilyAttendanceWriter;

    @Override
    public void deleteByNewFamilyIdIn(List<Long> newFamilyIds) {
        newFamilyAttendanceWriter.deleteByNewFamilyIdIn(newFamilyIds);
    }
}
