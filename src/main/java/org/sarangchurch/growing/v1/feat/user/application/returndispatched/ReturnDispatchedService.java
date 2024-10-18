package org.sarangchurch.growing.v1.feat.user.application.returndispatched;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.DispatchedReturner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnDispatchedService {
    private final DispatchedReturner dispatchedReturner;

    public void returnDispatched(Long dispatchUserId) {
        dispatchedReturner.returnDispatched(dispatchUserId);
    }
}
