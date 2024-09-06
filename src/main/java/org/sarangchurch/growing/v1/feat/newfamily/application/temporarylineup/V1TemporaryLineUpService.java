package org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyTemporaryLineUpManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1TemporaryLineUpService {
    private final V1NewFamilyTemporaryLineUpManager temporaryLineUpManager;

    public void temporaryLineUp(V1TemporaryLineUpRequest request) {
        temporaryLineUpManager.temporaryLineUp(request);
    }
}
