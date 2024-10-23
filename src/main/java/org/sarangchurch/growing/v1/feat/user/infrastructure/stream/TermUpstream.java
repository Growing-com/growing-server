package org.sarangchurch.growing.v1.feat.user.infrastructure.stream;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TermUpstream {
    private final TermService termService;

    public void emitByUserIds(List<Long> userIds) {
        termService.emitByUserIds(userIds);
    }
}
