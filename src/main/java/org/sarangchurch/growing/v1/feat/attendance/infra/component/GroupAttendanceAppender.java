package org.sarangchurch.growing.v1.feat.attendance.infra.component;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.attendance.Attendance;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.AttendanceWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.NewFamilyGroupDownstream;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.SmallGroupDownstream;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.term.TermDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupAttendanceAppender {
    private final TermDownstream termDownstream;
    private final SmallGroupDownstream smallGroupDownstream;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final AttendanceWriter attendanceWriter;

    @Transactional
    public void append(List<Attendance> attendances) {
        Attendance attendance = attendances.get(0);
        LocalDate attendanceDate = attendance.getDate();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        Long smallGroupId = attendance.getSmallGroupId();

        if (smallGroupId != null) {
            List<Long> userIds = attendances.stream()
                    .map(Attendance::getUserId)
                    .collect(Collectors.toList());

            boolean isValid = smallGroupDownstream.areValidUserIdsBySmallGroupId(userIds, smallGroupId);

            if (!isValid) {
                throw new IllegalArgumentException("일반 순모임에 속하지 않은 인원이 포함되어 있습니다.");
            }

            Pair<Term, Cody> pair = termDownstream.findTermAndCodyBySmallGroupId(smallGroupId);

            attendances.forEach(it -> {
                it.setTermId(pair.getFirst().getId());
                it.setCodyId(pair.getSecond().getId());
            });

            attendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
            attendanceWriter.saveAll(attendances);

            return;
        }

        Long newFamilyGroupId = attendance.getNewFamilyGroupId();

        if (newFamilyGroupId != null) {
            List<Long> userIds = attendances.stream()
                    .map(Attendance::getUserId)
                    .collect(Collectors.toList());

            boolean isValid = newFamilyGroupDownstream.areValidUserIdsByNewFamilyGroupId(userIds, newFamilyGroupId);

            if (!isValid) {
                throw new IllegalArgumentException("새가족 순모임에 속하지 않은 인원이 포함되어 있습니다.");
            }

            Pair<Term, Cody> pair = termDownstream.findTermAndCodyByNewFamilyGroupId(newFamilyGroupId);

            attendances.forEach(it -> {
                it.setTermId(pair.getFirst().getId());
                it.setCodyId(pair.getSecond().getId());
            });

            attendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
            attendanceWriter.saveAll(attendances);
        }
    }
}
