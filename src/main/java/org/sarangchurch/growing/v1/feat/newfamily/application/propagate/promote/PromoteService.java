package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.promote;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate.NewFamilyPromoter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyPromoter promoteManager;

    public void promote(PromoteRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(PromoteRequest.V1PromoteRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<LocalDate> promoteDates = request.getContent()
                        .stream()
                .map(PromoteRequest.V1PromoteRequestItem::getPromoteDate)
                .collect(Collectors.toList());

        promoteManager.promote(newFamilyIds, promoteDates);
    }
}
