package org.sarangchurch.growing.v2.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyPromoter;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilySmallGroupAssigner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineupService {
    private final NewFamilyPromoter newFamilyPromoter;
    private final NewFamilySmallGroupAssigner newFamilySmallGroupAssigner;

    public void lineup(Long newFamilyId, LineupRequest request) {
        if (request.getPromoteDate() != null) {
            newFamilyPromoter.promoteAndLineup(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());

            return;
        }

        // 등반 없이 일반 순모임 라인업(특이 케이스)
        newFamilySmallGroupAssigner.assign(newFamilyId, request.getSmallGroupId());
    }
}
