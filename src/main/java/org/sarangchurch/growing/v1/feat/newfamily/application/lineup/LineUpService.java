package org.sarangchurch.growing.v1.feat.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyLineUpManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineUpService {
    private final NewFamilyLineUpManager lineUpManager;

    public void lineUp(LineUpRequest request) {
        lineUpManager.lineUp(request);
    }
}
