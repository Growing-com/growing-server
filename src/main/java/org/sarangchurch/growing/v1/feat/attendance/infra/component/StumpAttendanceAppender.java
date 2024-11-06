package org.sarangchurch.growing.v1.feat.attendance.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.stumpattendance.StumpAttendance;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.StumpAttendanceWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.TermDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StumpAttendanceAppender {
    private final TermDownstream termDownstream;
    private final StumpAttendanceWriter stumpAttendanceWriter;

    @Transactional
    public void append(List<StumpAttendance> attendances) {
        LocalDate attendanceDate = attendances.get(0).getDate();
        Long termId = attendances.get(0).getTermId();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        List<Long> userIds = attendances.stream()
                .map(StumpAttendance::getUserId)
                .collect(Collectors.toList());

        boolean isValid = termDownstream.areValidStumpUserIdsByTermId(userIds, termId);

        if (!isValid) {
            throw new IllegalArgumentException("그루터기 인원이 아닌 지체가 포함되어 있습니다.");
        }

        stumpAttendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
        stumpAttendanceWriter.saveAll(attendances);
    }
}
