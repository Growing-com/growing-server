package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermUpstream {
    private final TermService termService;

    public void lineupUser(Long userId, Long smallGroupId) {
        termService.lineupUser(userId, smallGroupId);
    }
}
