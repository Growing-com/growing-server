package org.sarangchurch.growing.v2.newfamily.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLog;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLogRepository;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoter {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;

    public void promote(Long newFamilyId, LocalDate promoteDate, Long smallGroupId) {
        validateSmallGroup(smallGroupId);

        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        NewFamilyPromoteLog log = newFamilyPromoteLogRepository.save(
                NewFamilyPromoteLog.builder()
                        .promoteDate(promoteDate)
                        .smallGroupId(smallGroupId)
                        .build()
        );

        newFamily.promote(log.getId());

        // TODO: 일반 순원 생성
    }

    public void promote(Long newFamilyId, LocalDate promoteDate) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        NewFamilyPromoteLog log = newFamilyPromoteLogRepository.save(
                NewFamilyPromoteLog.builder()
                        .promoteDate(promoteDate)
                        .build()
        );

        newFamily.promote(log.getId());
    }

    private void validateSmallGroup(Long smallGroupId) {
        // TODO: 순모임 유효성 검사
    }
}
