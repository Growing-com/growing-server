package org.sarangchurch.growing.v2.attendance.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.attendance.application.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.attendance.domain.NewFamilyAttendance;
import org.sarangchurch.growing.v2.attendance.domain.NewFamilyAttendanceRepository;
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
        // TODO: 권한 검사(간사-> all, 리더-> 본인 소속)
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
