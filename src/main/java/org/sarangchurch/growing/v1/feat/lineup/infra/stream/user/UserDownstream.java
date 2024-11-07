package org.sarangchurch.growing.v1.feat.lineup.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

@Component("LineUp_User_Downstream")
@RequiredArgsConstructor
public class UserDownstream {
    private final UserService userService;

    public User findActiveByIdOrThrow(Long userId) {
        User user = userService.findById(userId);

        if (!user.isActive()) {
            throw new IllegalStateException("비활성 유저입니다.");
        }

        return user;
    }
}
