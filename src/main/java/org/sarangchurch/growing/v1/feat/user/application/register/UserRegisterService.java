package org.sarangchurch.growing.v1.feat.user.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infra.component.UserRegisterManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRegisterManager userRegisterManager;

    public void register(UserRegisterRequest request) {
        if (request.getSmallGroupId() == null) {
            userRegisterManager.register(request.toUser());
        } else {
            userRegisterManager.register(request.toUser(), request.getSmallGroupId());
        }
    }
}
