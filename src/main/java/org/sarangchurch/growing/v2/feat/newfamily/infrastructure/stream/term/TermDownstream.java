package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.sarangchurch.growing.v2.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public Term findTermBySmallGroupId(Long smallGroupId) {
        return termService.findTermBySmallGroupId(smallGroupId);
    }

    public Term findTerm(Long id) {
        return termService.findTerm(id);
    }
}
