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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyTemporaryLineUpManager {
    private final SmallGroupDownstream smallGroupDownstream;
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;

    @Transactional
    public void temporaryLineUp(List<Long> newFamilyIds, List<List<Long>> temporarySmallGroupIds) {
        smallGroupDownstream.validateAvailable(
                temporarySmallGroupIds
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );

        Pair<List<NewFamily>, List<NewFamilyPromoteLog>> pair = newFamilyPromoteLogFinder.findPromoteCandidatesByNewFamilyIds(newFamilyIds);
        List<NewFamily> newFamilies = pair.getFirst();
        List<NewFamilyPromoteLog> promoteLogs = pair.getSecond();

        for (int i = 0; i < newFamilyIds.size(); i++) {
            Long newFamilyId = newFamilyIds.get(i);
            List<Long> temporarySmallGroupIdList = temporarySmallGroupIds.get(i);

            NewFamily newFamily = newFamilies.stream()
                    .filter(el -> el.getId().equals(newFamilyId))
                    .findAny()
                    .orElseThrow();

            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updateTemporarySmallGroups(temporarySmallGroupIdList);
        }
    }
}
