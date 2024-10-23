package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilypromotelog.NewFamilyPromoteLogFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoteLogLookUpManager {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;

    public Pair<List<NewFamily>, List<NewFamilyPromoteLog>> findLineUpReadyByNewFamilyIds(List<Long> newFamilyIds) {
        // 등반전
        Pair<List<NewFamily>, List<NewFamilyPromoteLog>> pair = findBeforePromotedByNewFamilyIds(newFamilyIds);
        List<NewFamily> newFamilies = pair.getFirst();
        List<NewFamilyPromoteLog> promoteLogs = pair.getSecond();

        // 라인업 요청된 상태
        boolean containsBeforeRequest = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() == null);

        if (containsBeforeRequest) {
            throw new IllegalStateException("라인업 요청이 되지 않은 새가족이 포함되어 있습니다.");
        }

        return Pair.of(newFamilies, promoteLogs);
    }

    public Pair<List<NewFamily>, List<NewFamilyPromoteLog>> findBeforePromotedByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = newFamilyPromoteLogFinder.findByIdIn(promoteLogIds);

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        return Pair.of(newFamilies, promoteLogs);
    }
}
