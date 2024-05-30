package org.sarangchurch.growing.attendance.query.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AttendanceProgressResult {
    private final Week week;
    private final Long totalRegistered;
    private final Long totalProgressed;
    private final List<NotProgressedLeaders> notProgressedLeaders;

    @Getter
    @RequiredArgsConstructor
    public static class NotProgressedLeaders {
        private final Long userId;
        private final String name;
    }
}
