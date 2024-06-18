package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.term.TermUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoter {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;
    private final TermUpstream termUpstream;

    @Transactional
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

    @Transactional
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
