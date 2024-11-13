package org.sarangchurch.growing.v1.feat.term.infra.component.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermStatus;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TermActivator {
    private final TermFinder termFinder;

    @Transactional
    public void activate(Long termId) {
        Term targetActiveTerm = termFinder.findById(termId);
        Term currentActiveTerm = termFinder.findActiveOrThrow();

        if (!targetActiveTerm.statusEquals(TermStatus.LINE_UP)) {
            throw new IllegalStateException("라인업 상태의 텀만 활성화할 수 있습니다.");
        }

        currentActiveTerm.toClosedStatus();
        targetActiveTerm.toActiveStatus();
    }
}
