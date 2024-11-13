package org.sarangchurch.growing.v1.feat.term.infra.component.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermAppender {
    private final TermWriter termWriter;

    public void append(Term term) {
        termWriter.write(term.toLineUpStatus());
    }
}
