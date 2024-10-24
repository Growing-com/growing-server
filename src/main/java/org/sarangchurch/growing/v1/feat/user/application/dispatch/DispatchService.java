package org.sarangchurch.growing.v1.feat.user.application.dispatch;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.ActiveUserValidator;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserDispatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private final ActiveUserValidator activeUserValidator;
    private final UserDispatcher userDispatcher;

    public void dispatch(DispatchRequest request) {
        activeUserValidator.validateByUserIds(request.getUserIds());
        userDispatcher.dispatch(request.toDispatchedUsers());
    }
}
