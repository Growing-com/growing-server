package org.sarangchurch.growing.v1.feat.attendance.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class NormalAttendanceListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String codyName;
    private final String leaderName;
    private final List<NormalAttendanceListItemAttendItem> attendItems;

    @Getter
    @RequiredArgsConstructor
    public static class NormalAttendanceListItemAttendItem {
        @JsonIgnore
        private final Long userId;

        private final AttendanceStatus status;
        private final LocalDate date;
        private final String reason;
    }
}
