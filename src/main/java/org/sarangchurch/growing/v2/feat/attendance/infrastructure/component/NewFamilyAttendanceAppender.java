package org.sarangchurch.growing.v2.feat.attendance.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.feat.attendance.domain.newfamilyattendance.NewFamilyAttendance;
import org.sarangchurch.growing.v2.feat.attendance.domain.newfamilyattendance.NewFamilyAttendanceRepository;
import org.sarangchurch.growing.v2.feat.attendance.infrastructure.stream.newfamily.NewFamilyDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceAppender {
    private final NewFamilyDownstream newFamilyDownstream;
    private final NewFamilyAttendanceRepository newFamilyAttendanceRepository;

    @Transactional
    public void append(RegisterNewFamilyAttendanceRequest request) {
        List<NewFamilyAttendance> attendances = request.toEntities();

        List<Long> newFamilyIds = attendances.stream()
                .map(NewFamilyAttendance::getNewFamilyId)
                .collect(Collectors.toList());

        boolean exists = newFamilyDownstream.existsByIds(newFamilyIds);

        if (!exists) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다");
        }

        newFamilyAttendanceRepository.deleteByNewFamilyIdInAndDate(
                newFamilyIds,
                attendances.get(0).getDate()
        );

        newFamilyAttendanceRepository.saveAll(attendances);
    }
}
