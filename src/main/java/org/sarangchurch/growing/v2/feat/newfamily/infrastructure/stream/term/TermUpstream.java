package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.V1TermService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermUpstream {
    private final V1TermService termService;

    public void lineupUser(Long userId, Long smallGroupId) {
        termService.lineupUser(userId, smallGroupId);
    }
}
