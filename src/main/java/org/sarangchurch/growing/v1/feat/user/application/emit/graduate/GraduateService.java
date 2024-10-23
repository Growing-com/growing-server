package org.sarangchurch.growing.v1.feat.user.application.emit.graduate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserGraduateManager;
import org.sarangchurch.growing.v1.feat.user.infrastructure.stream.TermUpstream;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraduateService {
    private final TermUpstream termUpstream;
    private final UserGraduateManager graduateManager;

    public void graduate(GraduateRequest request) {
        List<Long> userIds = request.getUserIds();
        termUpstream.emitByUserIds(userIds);
        graduateManager.graduate(userIds, request.getGraduateDate());
    }
}
