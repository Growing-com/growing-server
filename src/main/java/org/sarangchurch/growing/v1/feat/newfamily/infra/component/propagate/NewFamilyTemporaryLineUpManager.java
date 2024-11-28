package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyTemporaryLineUpManager {
    private final SmallGroupDownstream smallGroupDownstream;
    private final NewFamilyFinder newFamilyFinder;

    @Transactional
    public void temporaryLineUp(List<Long> newFamilyIds, List<List<Long>> temporarySmallGroupIds) {
        List<Long> smallGroupIds = temporarySmallGroupIds
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        smallGroupDownstream.validateAvailable(smallGroupIds);

        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(0);

            newFamily.assignTemporarySmallGroups(temporarySmallGroupIds.get(i));
        }
    }
}
