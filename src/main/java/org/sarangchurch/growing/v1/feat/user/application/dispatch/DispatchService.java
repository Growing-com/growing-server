package org.sarangchurch.growing.v1.feat.user.application.dispatch;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserDispatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private final UserDispatcher dispatcher;

    public void dispatch(DispatchRequest request) {
        dispatcher.dispatch(request);
    }
}
