package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public Term findTerm(Long id) {
        return termService.findTerm(id);
    }
}
