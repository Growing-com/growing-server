package org.sarangchurch.growing.v2.term.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.user.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDownstream {
    private final UserService userService;

    public void userExistsById(Long userId) {
        // 호출을 통해 검사
        userService.findById(userId);
    }
}
