package org.sarangchurch.growing.v2.feat.newfamily.application.batchlneup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyPromoter;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilySmallGroupAssigner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BatchLineupService {
    private final NewFamilyPromoter newFamilyPromoter;
    private final NewFamilySmallGroupAssigner newFamilySmallGroupAssigner;

    @Transactional
    public void lineup(BatchLineupRequest request) {
        request.getRequests()
                .forEach(it -> {
                    Long newFamilyId = it.getNewFamilyId();
                    LocalDate promoteDate = it.getPromoteDate();
                    Long smallGroupId = it.getSmallGroupId();

                    if (promoteDate != null) {
                        newFamilyPromoter.promoteAndLineup(newFamilyId, promoteDate, smallGroupId);

                        return;
                    }

                    // 등반 없이 일반 순모임 라인업(특이 케이스)
                    newFamilySmallGroupAssigner.assign(newFamilyId, smallGroupId);
                });
    }
}
