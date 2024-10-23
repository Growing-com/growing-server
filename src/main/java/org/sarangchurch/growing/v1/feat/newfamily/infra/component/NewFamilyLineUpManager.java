package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyPromoteLogFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpManager {
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;
    private final SmallGroupDownstream smallGroupDownstream;

    @Transactional
    public void lineUp(List<Long> newFamilyIds, List<Long> smallGroupIds) {
        smallGroupDownstream.validateAvailable(smallGroupIds);

        Pair<List<NewFamily>, List<NewFamilyPromoteLog>> pair = newFamilyPromoteLogFinder.findPromoteCandidatesByNewFamilyIds(newFamilyIds);
        List<NewFamily> newFamilies = pair.getFirst();
        List<NewFamilyPromoteLog> promoteLogs = pair.getSecond();

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(i);
            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            Long smallGroupId = smallGroupIds.get(i);
            log.updateSmallGroupId(smallGroupId);
        }
    }
}
