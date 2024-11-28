package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.requestlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate.NewFamilyLineUpRequester;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestLineUpService {
    private final NewFamilyLineUpRequester requester;

    public void requestLineUp(RequestLineUpRequest request) {
        requester.requestLineUp(request.getNewFamilyIds());
    }
}
