package org.sarangchurch.growing.core.interfaces.v1.attendance;

import java.util.List;

public interface NewFamilyAttendanceService {
    void deleteByNewFamilyIdIn(List<Long> newFamilyIds);
}
