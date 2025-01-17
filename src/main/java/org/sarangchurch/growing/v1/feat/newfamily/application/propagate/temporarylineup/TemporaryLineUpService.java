package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.temporarylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate.NewFamilyTemporaryLineUpManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryLineUpService {
    private final NewFamilyTemporaryLineUpManager temporaryLineUpManager;

    public void temporaryLineUp(TemporaryLineUpRequest request) {
        temporaryLineUpManager.temporaryLineUp(
                request.getNewFamilyIds(),
                request.getTemporarySmallGroupIds()
        );
    }
}
