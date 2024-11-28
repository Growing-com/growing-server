package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupMemberUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoter {
    private final NewFamilyPromoteLogManager newFamilyPromoteLogManager;
    private final SmallGroupMemberUpstream smallGroupMemberUpstream;

    @Transactional
    public void promote(List<Long> newFamilyIds, List<LocalDate> promoteDates) {
        List<NewFamily> newFamilies = newFamilyPromoteLogManager.findPromoteCandidatesByNewFamilyIds(newFamilyIds);

        // 등반 처리
        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(0);

            newFamily.promote(promoteDates.get(i));
            smallGroupMemberUpstream.create(newFamily.getUserId(), newFamily.getSmallGroupId());
        }
    }
}
