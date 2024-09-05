package org.sarangchurch.growing.v1.feat.newfamily.application.requestlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyLineupRequester;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1RequestLineUpService {
    private final V1NewFamilyLineupRequester requester;

    public void requestLineUp(V1RequestLineUpRequest request) {
        requester.run(request.getNewFamilyIds());
    }
}
