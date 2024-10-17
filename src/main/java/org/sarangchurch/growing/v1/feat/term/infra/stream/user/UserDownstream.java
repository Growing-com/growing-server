package org.sarangchurch.growing.v1.feat.term.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDownstream {
    private final UserService userService;

    public User findById(Long id) {
        return userService.findById(id);
    }

    public List<User> findByIdIn(List<Long> ids) {
        return userService.findByIdIn(ids);
    }
}
