package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.LineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.SmallGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpManager {
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;
    private final SmallGroupDownstream smallGroupDownstream;

    @Transactional
    public void lineUp(LineUpRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(LineUpRequest.V1LineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<Long> smallGroupIds = request.getContent()
                .stream()
                .map(LineUpRequest.V1LineUpRequestItem::getSmallGroupId)
                .collect(Collectors.toList());

        smallGroupDownstream.validateAvailable(smallGroupIds);

        Pair<List<NewFamily>, List<NewFamilyPromoteLog>> pair = newFamilyPromoteLogFinder.findPromoteCandidatesByNewFamilyIds(newFamilyIds);
        List<NewFamily> newFamilies = pair.getFirst();
        List<NewFamilyPromoteLog> promoteLogs = pair.getSecond();

        for (LineUpRequest.V1LineUpRequestItem requestItem : request.getContent()) {
            Long smallGroupId = requestItem.getSmallGroupId();

            NewFamily newFamily = newFamilies.stream()
                    .filter(el -> el.getId().equals(requestItem.getNewFamilyId()))
                    .findAny()
                    .orElseThrow();

            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updateSmallGroupId(smallGroupId);
        }
    }
}
