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
        validateData(attendances);

        Long smallGroupId = attendances.get(0).getSmallGroupId();
        Long newFamilyGroupId = attendances.get(0).getNewFamilyGroupId();

        if (smallGroupId != null) {
            handleSmallGroupAttendance(attendances);
        } else if (newFamilyGroupId != null) {
            handleNewFamilyGroupAttendance(attendances);
        }
    }

    private void validateData(List<Attendance> attendances) {
        LocalDate attendanceDate = attendances.get(0).getDate();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        boolean isAllSmallGroupAttendance = attendances.stream()
                .allMatch(Attendance::isSmallGroupAttendance);

        boolean isAllNewFamilyGroupAttendance = attendances.stream()
                .allMatch(Attendance::isNewFamilyGroupAttendance);

        if (isAllSmallGroupAttendance) {
            Long smallGroupId = attendances.get(0).getSmallGroupId();

            boolean smallGroupIdMatch = attendances.stream()
                    .allMatch(it -> smallGroupId.equals(it.getSmallGroupId()));

            if (!smallGroupIdMatch) {
                throw new IllegalArgumentException("모든 일반 순모임 id는 동일해야합니다.");
            }
        } else if (isAllNewFamilyGroupAttendance) {
            Long newFamilyGroupId = attendances.get(0).getNewFamilyGroupId();

            boolean newFamilyGroupIdMatch = attendances.stream()
                    .allMatch(it -> newFamilyGroupId.equals(it.getNewFamilyGroupId()));

            if (!newFamilyGroupIdMatch) {
                throw new IllegalArgumentException("모든 새가족 순모임 id는 동일해야합니다.");
            }
        } else {
            throw new IllegalArgumentException("출석 데이터는 출석 날짜가 같고 일반 순모임 출석, 새가족 순모임 출석중 하나여야만 합니다.");
        }
    }

    private void handleSmallGroupAttendance(List<Attendance> attendances) {
        Long smallGroupId = attendances.get(0).getSmallGroupId();
        LocalDate attendanceDate = attendances.get(0).getDate();

        List<Long> userIds = attendances.stream()
                .map(Attendance::getUserId)
                .collect(Collectors.toList());

        boolean isValid = smallGroupDownstream.areValidUserIdsBySmallGroupId(userIds, smallGroupId);

        if (!isValid) {
            throw new IllegalArgumentException("일반 순모임에 속하지 않은 인원이 포함되어 있습니다.");
        }

        Pair<Term, Cody> pair = termDownstream.findTermAndCodyBySmallGroupId(smallGroupId);
        Term term = pair.getFirst();
        Cody cody = pair.getSecond();

        if (!term.isActive()) {
            throw new IllegalStateException("비활성 텀의 순모임을 출석체크 할 수 없습니다.");
        }

        attendances.forEach(it -> {
            it.setTermId(term.getId());
            it.setCodyId(cody.getId());
        });

        attendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
        attendanceWriter.saveAll(attendances);
    }

    private void handleNewFamilyGroupAttendance(List<Attendance> attendances) {
        Long newFamilyGroupId = attendances.get(0).getNewFamilyGroupId();
        LocalDate attendanceDate = attendances.get(0).getDate();

        List<Long> userIds = attendances.stream()
                .map(Attendance::getUserId)
                .collect(Collectors.toList());

        boolean isValid = newFamilyGroupDownstream.areValidUserIdsByNewFamilyGroupId(userIds, newFamilyGroupId);

        if (!isValid) {
            throw new IllegalArgumentException("새가족 순모임에 속하지 않은 인원이 포함되어 있습니다.");
        }

        Pair<Term, Cody> pair = termDownstream.findTermAndCodyByNewFamilyGroupId(newFamilyGroupId);
        Term term = pair.getFirst();
        Cody cody = pair.getSecond();

        if (!term.isActive()) {
            throw new IllegalStateException("비활성 텀의 순모임을 출석체크 할 수 없습니다.");
        }

        attendances.forEach(it -> {
            it.setTermId(term.getId());
            it.setCodyId(cody.getId());
        });

        attendanceWriter.deleteByUserIdInAndDate(userIds, attendanceDate);
        attendanceWriter.saveAll(attendances);
    }
}
