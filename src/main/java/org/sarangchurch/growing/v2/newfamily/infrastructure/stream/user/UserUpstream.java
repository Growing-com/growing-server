package org.sarangchurch.growing.v2.newfamily.infrastructure.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.user.UserService;
import org.sarangchurch.growing.v2.user.domain.User;
import org.springframework.stereotype.Component;

@Component("NewFamily_User_Upstream")
@RequiredArgsConstructor
public class UserUpstream {
    private final UserService userService;

    public User register(User user) {
        return userService.register(user);
    }
}
