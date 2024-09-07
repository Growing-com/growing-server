package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.V1TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class V1TermDownstream {
    private final V1TermService termService;

    public Term findTerm(Long id) {
        return termService.findTerm(id);
    }
}
