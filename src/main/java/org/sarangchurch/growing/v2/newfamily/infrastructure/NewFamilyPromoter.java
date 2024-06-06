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
    private final TermUpstream termUpstream;

    public void promoteAndLineup(Long newFamilyId, LocalDate promoteDate, Long smallGroupId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        NewFamilyPromoteLog log = newFamilyPromoteLogRepository.save(
                NewFamilyPromoteLog.builder()
                        .promoteDate(promoteDate)
                        .smallGroupId(smallGroupId)
                        .build()
        );

        newFamily.promote(log.getId());

        termUpstream.lineupUser(newFamily.getUserId(), smallGroupId);
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
}
