package org.sarangchurch.growing.attendance.query.attendance;

import lombok.Builder;
import lombok.Getter;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AttendanceSearchResult {
    private final String leaderName;
    private final String managerName;
    private final Long userId;
    private final String userName;
    private final Integer grade;
    private final Sex sex;
    private final List<AttendanceItem> attendanceItems = new ArrayList<>();

    @Builder
    public AttendanceSearchResult(String leaderName, String managerName, Long userId, String userName, Integer grade, Sex sex) {
        this.leaderName = leaderName;
        this.managerName = managerName;
        this.userId = userId;
        this.userName = userName;
        this.grade = grade;
        this.sex = sex;
    }

    @Getter
    public static class AttendanceItem {
        private final Long attendanceId;
        private final AttendanceStatus status;
        private final Week week;
        private final String etc;

        @Builder
        public AttendanceItem(Long attendanceId, AttendanceStatus status, Week week, String etc) {
            this.attendanceId = attendanceId;
            this.status = status;
            this.week = week;
            this.etc = etc;
        }
    }
}
