package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyGroupFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.TermDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupValidator {
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final TermDownstream termDownstream;

    public void validateAvailable(Long newFamilyGroupId) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);

        Term term = termDownstream.findTerm(newFamilyGroup.getTermId());

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작하지 않은 텀에 라인업할 수 없습니다.");
        }
    }
}
