package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineUpTermFinder {
    private final TermService termService;

    public Term findByIdOrThrow(Long termId) {
        Term term = termService.findTerm(termId);

        if (!term.stateEquals(TermStatus.LINE_UP)) {
            throw new IllegalStateException("텀이 라인업 상태가 아닙니다.");
        }

        return term;
    }
}
