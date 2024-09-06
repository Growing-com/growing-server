package org.sarangchurch.growing.v1.feat.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyLineOutManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1LineOutService {
    private final V1NewFamilyLineOutManager lineOutManager;

    public void lineOut(V1LineOutRequest request) {
        lineOutManager.lineOut(request);
    }
}
