package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.infra.data.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodyUpdater {
    private final CodyFinder codyFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;

    @Transactional
    public void update(Long codyId, List<Long> requestedSmallGroupIds) {
        Cody cody = codyFinder.findByIdOrThrow(codyId);

        // 활성 텀 검증
        termFinder.findActiveByIdOrThrow(cody.getTermId());

        // 코디 기존 소속 순모임 검증
        HashSet<Long> existingSmallGroupIdSet = smallGroupFinder.findByCodyId(codyId)
                .stream()
                .map(SmallGroup::getId)
                .collect(Collectors.toCollection(HashSet::new));

        HashSet<Long> requestedSmallGroupIdSet = new HashSet<>(requestedSmallGroupIds);

        if (!requestedSmallGroupIdSet.containsAll(existingSmallGroupIdSet)) {
            throw new IllegalArgumentException("기존에 존재하던 순모임을 모두 포함해서 요청해야합니다.");
        }

        // 텀 일치 검증
        List<SmallGroup> smallGroups = smallGroupFinder.findByIdInOrThrow(requestedSmallGroupIds);

        boolean containsDifferentTerm = smallGroups.stream()
                .anyMatch(it -> !it.getTermId().equals(cody.getTermId()));

        if (containsDifferentTerm) {
            throw new IllegalStateException("코디와 순모임은 같은 텀에 속해있어야합니다.");
        }

        // 수정
        smallGroups.forEach(it -> it.updateCody(cody));
    }
}
