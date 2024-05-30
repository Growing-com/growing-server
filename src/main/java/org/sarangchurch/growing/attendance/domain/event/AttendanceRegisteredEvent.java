package org.sarangchurch.growing.attendance.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;

@Getter
@RequiredArgsConstructor
public class AttendanceRegisteredEvent {
    private final Week week;
}
