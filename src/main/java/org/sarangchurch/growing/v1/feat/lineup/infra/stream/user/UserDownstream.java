package org.sarangchurch.growing.v1.feat.lineup.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<User> findActiveByIdInOrThrow(List<Long> userIds) {
        List<User> users = userService.findByIdIn(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        if (users.stream().anyMatch(it -> !it.isActive())) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }

        return users;
    }

    public List<User> findAll() {
        return userService.findAll();
    }
}
