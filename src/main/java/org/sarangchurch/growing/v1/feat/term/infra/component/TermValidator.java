package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermValidator {
    private final TermFinder termFinder;

    public void validateActive(Long termId) {
        Term term = termFinder.findById(termId);

        if (!term.isActive()) {
            throw new IllegalStateException("텀이 활성 상태가 아닙니다.");
        }
    }
}
