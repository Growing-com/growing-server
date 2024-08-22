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
import java.util.Optional;

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

        Optional<NewFamilyPromoteLog> newFamilyPromoteLogOptional =
                newFamilyPromoteLogRepository.findByNewFamilyId(newFamily.getId());

        // 등반 + 라인업
        if (newFamilyPromoteLogOptional.isEmpty()) {
            NewFamilyPromoteLog log = NewFamilyPromoteLog.builder()
                    .newFamilyId(newFamily.getId())
                    .promoteDate(promoteDate)
                    .smallGroupId(smallGroupId)
                    .build();

            NewFamilyPromoteLog savedLog = newFamilyPromoteLogRepository.save(log);

            newFamily.promote(savedLog.getId());

            termUpstream.lineupUser(newFamily.getUserId(), smallGroupId);

            return;
        }

        // 등반은 되어있고 라인업만 하는 경우
        // API 단위에서 분리 예정
        // 케이스 고려해서 QA 시나리오 작성해야할듯
        NewFamilyPromoteLog log = newFamilyPromoteLogOptional.get();

        log.updateSmallGroupId(smallGroupId);

        newFamily.setSmallGroup(smallGroupId);

        termUpstream.lineupUser(newFamily.getUserId(), smallGroupId);
    }

    @Transactional
    public void promote(Long newFamilyId, LocalDate promoteDate) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        NewFamilyPromoteLog log = newFamilyPromoteLogRepository.save(
                NewFamilyPromoteLog.builder()
                        .newFamilyId(newFamily.getId())
                        .promoteDate(promoteDate)
                        .build()
        );

        newFamily.promote(log.getId());
    }
}
