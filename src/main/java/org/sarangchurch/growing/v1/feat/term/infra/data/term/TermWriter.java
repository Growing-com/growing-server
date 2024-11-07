package org.sarangchurch.growing.v1.feat.term.infra.data.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermWriter {
    private final TermRepository termRepository;

    public void write(Term term) {
        termRepository.save(term);
    }
}
