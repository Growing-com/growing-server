package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupValidator {
    private final SmallGroupRepository smallGroupRepository;
    private final TermRepository termRepository;

    public void validateAvailable(List<Long> smallGroupIds) {
        List<SmallGroup> smallGroups = smallGroupRepository.findByIdIn(smallGroupIds);

        boolean containsInvalidId = smallGroupIds.stream()
                .anyMatch(smallGroupId ->
                        smallGroups.stream()
                                .filter(smallGroup -> smallGroup.getId().equals(smallGroupId))
                                .findAny()
                                .isEmpty()
                );

        if (containsInvalidId) {
            throw new IllegalArgumentException("존재하지 않는 일반 순모임이 포함되어 있습니다.");
        }

        List<Long> termIds = smallGroups.stream()
                .map(SmallGroup::getTermId)
                .collect(Collectors.toList());

        if (termIds.size() != 1) {
            throw new IllegalStateException("모든 가용한 순모임은 하나의 텀에만 속해있어야합니다.");
        }

        Term term = termRepository.findById(termIds.get(0))
                .orElseThrow();

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작하지 않은 텀에 라인업할 수 없습니다.");
        }
    }
}
