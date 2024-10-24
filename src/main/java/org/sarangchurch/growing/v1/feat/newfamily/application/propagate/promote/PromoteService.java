package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.promote;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate.NewFamilyPromoter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyPromoter promoteManager;

    public void promote(PromoteRequest request) {
        promoteManager.promote(
                request.getNewFamilyIds(),
                request.getPromoteDates()
        );
    }
}
