package org.sarangchurch.growing.v1.feat.attendance.application.registergroupattendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.GroupType;
import org.sarangchurch.growing.v1.feat.attendance.domain.AttendanceStatus;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RegisterGroupAttendanceRequest {
    @NotNull(message = "텀 id를 입력해주세요.")
    private Long termId;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate date;

    @NotEmpty(message = "출석 리스트를 입력해주세요.")
    @Valid
    private List<GroupAttendanceItems> attendanceItems;

    @NotNull(message = "새가족 순모임/순모임 여부를 입력해주세요.")
    private GroupType groupType;

    @NotNull(message = "그룹 id를 입력해주세요.")
    private Long groupId;

    public void toLatestSunday() {
        date = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    public List<Attendance> toEntities(Long codyId) {
        if (groupType == GroupType.NEW_FAMILY_GROUP) {
            return attendanceItems.stream()
                    .map(it -> Attendance.builder()
                            .userId(it.userId)
                            .termId(termId)
                            .codyId(codyId)
                            .newFamilyGroupId(groupId)
                            .date(date)
                            .status(it.status)
                            .reason(it.reason)
                            .build())
                    .collect(Collectors.toList());
        } else {
            return attendanceItems.stream()
                    .map(it -> Attendance.builder()
                            .userId(it.userId)
                            .termId(termId)
                            .codyId(codyId)
                            .smallGroupId(groupId)
                            .date(date)
                            .status(it.status)
                            .reason(it.reason)
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GroupAttendanceItems {
        @NotNull(message = "지체 id를 입력해주세요.")
        private Long userId;

        @NotNull(message = "출석 정보를 입력해주세요.")
        private AttendanceStatus status;

        private String reason;
    }
}
