package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupMemberUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoter {
    private final NewFamilyFinder newFamilyFinder;
    private final SmallGroupMemberUpstream smallGroupMemberUpstream;

    @Transactional
    public void promote(List<Long> newFamilyIds, List<LocalDate> promoteDates) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(i);

            newFamily.promote(promoteDates.get(i));
            smallGroupMemberUpstream.create(newFamily.getUserId(), newFamily.getSmallGroupId());
        }
    }
}
