package org.sarangchurch.growing.v1.feat.term.application.updatecody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.CodyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCodyService {
    private final CodyUpdater codyUpdater;

    public void update(Long codyId, UpdateCodyRequest request) {
        codyUpdater.update(codyId, request.getSmallGroupIds());
    }
}
