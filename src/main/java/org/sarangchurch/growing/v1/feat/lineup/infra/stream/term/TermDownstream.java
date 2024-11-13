package org.sarangchurch.growing.v1.feat.lineup.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("LineUp_Term_Downstream")
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public Optional<Term> findLineUpTerm() {
        return termService.findLineUpTerm();
    }
}
