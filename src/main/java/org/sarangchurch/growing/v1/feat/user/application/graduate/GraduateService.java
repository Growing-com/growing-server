package org.sarangchurch.growing.v1.feat.user.application.graduate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserGraduateManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraduateService {
    private final UserGraduateManager graduateManager;

    public void graduate(GraduateRequest request) {
        graduateManager.graduate(request.getUserIds(), request.getGraduateDate());
    }
}
