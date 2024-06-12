package org.sarangchurch.growing.v2.user.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.user.UserService;
import org.sarangchurch.growing.v2.user.domain.User;
import org.sarangchurch.growing.v2.user.infrastructure.UserAppender;
import org.sarangchurch.growing.v2.user.infrastructure.UserFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAppender userAppender;
    private final UserFinder userFinder;

    @Override
    public User register(User user) {
        // 이름 중복 검사 해야하나?
        return userAppender.append(user);
    }

    @Override
    public User findById(Long id) {
        return userFinder.findById(id);
    }
}
