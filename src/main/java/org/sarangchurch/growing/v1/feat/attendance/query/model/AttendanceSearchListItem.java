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
public class AttendanceSearchListItem {
    private final Long userId;
    private final String name;
    private final Sex sex;
    private final Integer grade;
    private final String codyName;
    private final String leaderName;
    private final List<AttendanceSearchListItemAttendItem> attendItems;

    @Getter
    @RequiredArgsConstructor
    public static class AttendanceSearchListItemAttendItem {
        @JsonIgnore
        private final Long userId;
        @JsonIgnore
        private final String name;
        @JsonIgnore
        private final Sex sex;
        @JsonIgnore
        private final Integer grade;

        private final AttendanceStatus status;
        private final LocalDate date;
        private final String reason;

        public boolean matchByUserAndDate(Long userId, LocalDate date) {
            return this.userId.equals(userId) && this.date.equals(date);
        }
    }
}
