package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.attendance;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.attendance.NewFamilyAttendanceService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceUpstream {
    private final NewFamilyAttendanceService newFamilyAttendanceService;

    public void deleteByNewFamilyIdIn(List<Long> newFamilyIds) {
        newFamilyAttendanceService.deleteByNewFamilyIdIn(newFamilyIds);
    }
}
