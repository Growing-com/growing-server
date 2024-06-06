package org.sarangchurch.growing.v2.newfamily.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroup;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.term.TermDownstream;
import org.sarangchurch.growing.v2.term.domain.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupValidator {
    private final NewFamilyGroupRepository newFamilyGroupRepository;
    private final TermDownstream termDownstream;

    public void validateAvailable(Long newFamilyGroupId) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupRepository.findById(newFamilyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반입니다."));

        Term term = termDownstream.findTerm(newFamilyGroup.getTermId());

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작하지 않은 텀에 라인업할 수 없습니다.");
        }
    }
}
