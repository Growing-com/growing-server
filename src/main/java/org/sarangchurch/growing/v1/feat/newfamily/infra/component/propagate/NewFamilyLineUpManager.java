package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpManager {
    private final NewFamilyPromoteLogManager newFamilyPromoteLogManager;
    private final SmallGroupDownstream smallGroupDownstream;

    @Transactional
    public void lineUp(List<Long> newFamilyIds, List<Long> smallGroupIds) {
        smallGroupDownstream.validateAvailable(smallGroupIds);

        List<NewFamily> newFamilies = newFamilyPromoteLogManager.findLineUpRequestedByNewFamilyIds(newFamilyIds);

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(i);

            newFamily.assignSmallGroup(smallGroupIds.get(i));
        }
    }
}
