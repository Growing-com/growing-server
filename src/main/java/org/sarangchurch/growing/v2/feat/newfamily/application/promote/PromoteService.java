package org.sarangchurch.growing.v2.feat.newfamily.application.promote;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyPromoter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyPromoter newFamilyPromoter;

    public void promote(Long newFamilyId, PromoteRequest request) {
        if (request.getSmallGroupId() != null) {
            newFamilyPromoter.promoteAndLineup(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());

            return;
        }

        // 등반만 먼저 시키는 경우(특이 케이스)
        newFamilyPromoter.promote(newFamilyId, request.getPromoteDate());
    }
}
