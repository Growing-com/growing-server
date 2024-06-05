package org.sarangchurch.growing.v2.newfamily.application.promote;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyPromoter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyPromoter newFamilyPromoter;

    public void promote(Long newFamilyId, PromoteRequest request) {
        if (request.getSmallGroupId() != null) {
            newFamilyPromoter.promote(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());
        } else {
            newFamilyPromoter.promote(newFamilyId, request.getPromoteDate());
        }
    }
}
