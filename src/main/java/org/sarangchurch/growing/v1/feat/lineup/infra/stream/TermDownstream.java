package org.sarangchurch.growing.v1.feat.lineup.infra.stream;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.springframework.stereotype.Component;

@Component("LineUp_Term_Downstream")
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;
}
