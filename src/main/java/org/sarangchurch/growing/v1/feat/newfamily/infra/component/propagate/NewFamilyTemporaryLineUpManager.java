package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyTemporaryLineUpManager {
    private final SmallGroupDownstream smallGroupDownstream;
    private final NewFamilyPromoteLogManager newFamilyPromoteLogManager;

    @Transactional
    public void temporaryLineUp(List<Long> newFamilyIds, List<List<Long>> temporarySmallGroupIds) {
        smallGroupDownstream.validateAvailable(
                temporarySmallGroupIds
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );

        Pair<List<NewFamily>, List<NewFamilyPromoteLog>> pair = newFamilyPromoteLogManager.findLineUpReadyByNewFamilyIds(newFamilyIds);
        List<NewFamily> newFamilies = pair.getFirst();
        List<NewFamilyPromoteLog> promoteLogs = pair.getSecond();

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(0);
            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updateTemporarySmallGroups(temporarySmallGroupIds.get(i));
        }
    }
}
