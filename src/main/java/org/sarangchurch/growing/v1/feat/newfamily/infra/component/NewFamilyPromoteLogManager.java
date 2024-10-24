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
public class NewFamilyPromoteLogManager {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;

    /**
     * 모든 새가족이 라인업 단계인 것을 보장
     */
    public Pair<List<NewFamily>, List<NewFamilyPromoteLog>> findLineUpReadyByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        // 라인업은 요청된 상태(NewFamilyPromoteLog 존재해야함)
        boolean containsNotRequested = newFamilies.stream()
                .anyMatch(el -> !el.hasPromoteLog());

        if (containsNotRequested) {
            throw new IllegalStateException("라인업 요청이 되지 않은 새가족이 포함되어 있습니다.");
        }

        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = newFamilyPromoteLogFinder.findByIdIn(promoteLogIds);

        if (newFamilies.size() != promoteLogs.size()) {
            throw new IllegalStateException("등반 기록이 존재하지 않는 새가족이 포함되어 있습니다.");
        }

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반한 새가족이 포함되어 있습니다.");
        }

        return Pair.of(newFamilies, promoteLogs);
    }

    /**
     * 모든 새가족이 등반 준비(라인업 완료) 상태임을 보장
     */
    public Pair<List<NewFamily>, List<NewFamilyPromoteLog>> findPromoteReadyByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        List<NewFamilyPromoteLog> promoteLogs = newFamilyPromoteLogFinder.findByIdIn(
                newFamilies.stream()
                        .map(NewFamily::getNewFamilyPromoteLogId)
                        .collect(Collectors.toList())
        );

        long promoteCandidateCount = promoteLogs.stream()
                .filter(NewFamilyPromoteLog::isPromoteCandidate)
                .count();

        if (promoteCandidateCount != newFamilyIds.size()) {
            throw new IllegalArgumentException("등반 후보가 아닌 새가족이 포함되어 있습니다.");
        }

        return Pair.of(newFamilies, promoteLogs);
    }

    /**
     * 모든 새가족이 등반하지 않았음을 보장
     */
    public void validateBeforePromotedByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        List<NewFamilyPromoteLog> promoteLogs = newFamilyPromoteLogFinder.findByIdIn(
                newFamilies.stream()
                        .map(NewFamily::getNewFamilyPromoteLogId)
                        .collect(Collectors.toList())
        );

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }
    }
}
