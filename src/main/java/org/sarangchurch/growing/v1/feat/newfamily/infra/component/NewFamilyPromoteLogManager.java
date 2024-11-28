package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoteLogManager {
    private final NewFamilyFinder newFamilyFinder;

    public List<NewFamily> findLineUpRequestedByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean allLineUpRequested = newFamilies.stream()
                .allMatch(it -> it.statusEquals(NewFamilyStatus.LINE_UP_REQUESTED));

        if (!allLineUpRequested) {
            throw new IllegalArgumentException("라인업 요청 상태가 아닌 새가족이 포함되어있습니다.");
        }

        return newFamilies;
    }

    public List<NewFamily> findPromoteCandidatesByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean allPromoteCandidate = newFamilies.stream()
                .allMatch(it -> it.statusEquals(NewFamilyStatus.PROMOTE_CANDIDATE));

        if (!allPromoteCandidate) {
            throw new IllegalArgumentException("등반 후보가 아닌 새가족이 포함되어 있습니다.");
        }

        return newFamilies;
    }

    public void validateBeforePromotedByNewFamilyIds(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean containsPromoted = newFamilies.stream()
                .anyMatch(it -> it.statusEquals(NewFamilyStatus.PROMOTED));

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }
    }
}
