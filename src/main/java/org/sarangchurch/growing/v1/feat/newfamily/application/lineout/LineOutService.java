package org.sarangchurch.growing.v1.feat.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyLineOutManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineOutService {
    private final NewFamilyLineOutManager lineOutManager;

    public void lineOut(LineOutRequest request) {
        lineOutManager.lineOut(request);
    }
}
