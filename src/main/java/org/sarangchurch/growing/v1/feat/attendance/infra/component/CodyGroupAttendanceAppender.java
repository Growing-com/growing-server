package org.sarangchurch.growing.v1.feat.attendance.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.attendance.AttendanceWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.CodyDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodyGroupAttendanceAppender {
    private final CodyDownstream codyDownstream;
    private final AttendanceWriter attendanceWriter;

    @Transactional
    public void append(Long codyId, List<Attendance> attendances) {
        LocalDate attendanceDate = attendances.get(0).getDate();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        List<Long> userIds = attendances.stream()
                .map(Attendance::getUserId)
                .collect(Collectors.toList());

        boolean areValidMemberUserIds = codyDownstream.areValidMemberUserIdsByCodyId(codyId, userIds);

        if (!areValidMemberUserIds) {
            throw new IllegalArgumentException("코디 소속 순장, 순원들만 출석체크할 수 있습니다.");
        }

        attendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
        attendanceWriter.saveAll(attendances);
    }
}
