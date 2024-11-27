package org.sarangchurch.growing.v1.feat.attendance.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.attendance.AttendanceWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.TermDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodyGroupAttendanceAppender {
    private final TermDownstream termDownstream;
    private final AttendanceWriter attendanceWriter;

    @Transactional
    public void append(Long codyId, List<Attendance> attendances) {
        validateData(attendances);

        boolean containsCody = termDownstream.containsCodyByTermIdAndCodyId(attendances.get(0).getTermId(), codyId);

        if (!containsCody) {
            throw new IllegalArgumentException("텀에 소속된 코디가 없습니다.");
        }

        List<Long> userIds = attendances.stream()
                .map(Attendance::getUserId)
                .collect(Collectors.toList());

        boolean areValidMemberUserIds = termDownstream.areValidMemberUserIdsByCodyId(userIds, codyId);

        if (!areValidMemberUserIds) {
            throw new IllegalArgumentException("코디 소속 순장, 순원들만 출석체크할 수 있습니다.");
        }

        attendanceWriter.deleteByUserIdInAndDate(userIds, attendances.get(0).getDate());
        attendanceWriter.saveAll(attendances);
    }

    private void validateData(List<Attendance> attendances) {
        LocalDate attendanceDate = attendances.get(0).getDate();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        Long codyId = attendances.get(0).getCodyId();

        boolean allCodiesAreEqual = attendances.stream()
                .allMatch(it -> it.getCodyId().equals(codyId));

        if (!allCodiesAreEqual) {
            throw new IllegalArgumentException("모든 출석 데이터의 담당 코디는 같아야합니다.");
        }

        Long termId = attendances.get(0).getTermId();

        boolean areTermsAreEqual = attendances.stream()
                .allMatch(it -> it.getTermId().equals(termId));

        if (!areTermsAreEqual) {
            throw new IllegalArgumentException("모든 출석 데이터의 소속 텀은 같아야합니다.");
        }
    }
}
