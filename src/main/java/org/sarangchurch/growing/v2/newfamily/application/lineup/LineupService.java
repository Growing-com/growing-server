package org.sarangchurch.growing.v2.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyPromoter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineupService {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoter newFamilyPromoter;

    public void lineup(Long newFamilyId, LineupRequest request) {
        if (request.getPromoteDate() != null) {
            // 등반 + 라인업
            newFamilyPromoter.promote(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());
        } else {
            // 라인업
        }
    }
}
