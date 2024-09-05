package org.sarangchurch.growing.v1.feat.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyLineUpManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1LineUpService {
    private final V1NewFamilyLineUpManager lineUpManager;

    public void lineUp(V1LineUpRequest request) {
        lineUpManager.lineUp(request);
    }
}
