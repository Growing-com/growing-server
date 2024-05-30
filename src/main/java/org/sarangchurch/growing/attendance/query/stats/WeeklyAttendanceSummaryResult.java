package org.sarangchurch.growing.attendance.query.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;

@Getter
@RequiredArgsConstructor
public class WeeklyAttendanceSummaryResult {
    private final Week week;
    private final Long totalRegistered;
    private final Long totalAttendance;
    private final Long totalOnline;
    private final Long totalAbsent;
    private final Long maleRegistered;
    private final Long maleAttendance;
    private final Long femaleRegistered;
    private final Long femaleAttendance;
    private final Long newComerRegistered;
    private final Long newComerAttendance;
    private final Long newVisited;
}
