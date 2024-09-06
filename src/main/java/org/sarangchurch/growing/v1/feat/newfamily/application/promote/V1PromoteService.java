package org.sarangchurch.growing.v1.feat.newfamily.application.promote;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1PromoteManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1PromoteService {
    private final V1PromoteManager promoteManager;

    public void promote(V1PromoteRequest request) {
        promoteManager.promote(request);
    }
}
