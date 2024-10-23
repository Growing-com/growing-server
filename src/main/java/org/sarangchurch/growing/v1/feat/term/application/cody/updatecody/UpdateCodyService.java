package org.sarangchurch.growing.v1.feat.term.application.cody.updatecody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCodyService {
    private final CodyUpdater codyUpdater;

    public void update(Long codyId, UpdateCodyRequest request) {
        codyUpdater.update(codyId, request.getSmallGroupIds());
    }
}
