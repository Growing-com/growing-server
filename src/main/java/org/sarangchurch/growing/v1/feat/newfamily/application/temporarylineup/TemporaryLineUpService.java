package org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyTemporaryLineUpManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryLineUpService {
    private final NewFamilyTemporaryLineUpManager temporaryLineUpManager;

    public void temporaryLineUp(TemporaryLineUpRequest request) {
        temporaryLineUpManager.temporaryLineUp(request);
    }
}
