package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoteLogFinder {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogRepository promoteLogRepository;

    public Pair<List<NewFamily>, List<NewFamilyPromoteLog>> findPromoteCandidatesByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdIn(newFamilyIds);

        // 라인업 가능 검증
        boolean containsBeforeRequest = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() == null);

        if (containsBeforeRequest) {
            throw new IllegalStateException("라인업 요청이 되지 않은 새가족이 포함되어 있습니다.");
        }

        // 등반 이전 검증
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = promoteLogRepository.findByIdIn(promoteLogIds);

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        return Pair.of(newFamilies, promoteLogs);
    }
}
