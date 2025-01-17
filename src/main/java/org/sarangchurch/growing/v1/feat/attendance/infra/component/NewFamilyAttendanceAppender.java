package org.sarangchurch.growing.v1.feat.attendance.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendance;
import org.sarangchurch.growing.v1.feat.attendance.infra.data.newfamilyattendance.NewFamilyAttendanceWriter;
import org.sarangchurch.growing.v1.feat.attendance.infra.stream.newfamily.NewFamilyDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceAppender {
    private final NewFamilyDownstream newFamilyDownstream;
    private final NewFamilyAttendanceWriter newFamilyAttendanceWriter;

    @Transactional
    public void append(List<NewFamilyAttendance> attendances) {
        LocalDate attendanceDate = attendances.get(0).getDate();

        boolean allDatesAreEqual = attendances.stream()
                .allMatch(it -> it.getDate().equals(attendanceDate));

        if (!allDatesAreEqual) {
            throw new IllegalArgumentException("모든 출석 날짜는 동일해야합니다");
        }

        List<Long> newFamilyIds = attendances.stream()
                .map(NewFamilyAttendance::getNewFamilyId)
                .collect(Collectors.toList());

        boolean areCurrentNewFamilies = newFamilyDownstream.areCurrentNewFamiliesByIds(newFamilyIds);

        if (!areCurrentNewFamilies) {
            throw new IllegalArgumentException("새가족이 아닌 지체가 포함되어 있습니다");
        }

        newFamilyAttendanceWriter.deleteByNewFamilyIdInAndDate(newFamilyIds, attendanceDate);
        newFamilyAttendanceWriter.saveAll(attendances);
    }
}
