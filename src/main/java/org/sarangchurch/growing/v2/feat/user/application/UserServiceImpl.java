package org.sarangchurch.growing.v2.feat.user.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Sex;
import org.sarangchurch.growing.v2.core.interfaces.user.UserService;
import org.sarangchurch.growing.v2.feat.user.domain.User;
import org.sarangchurch.growing.v2.feat.user.infrastructure.component.UserAppender;
import org.sarangchurch.growing.v2.feat.user.infrastructure.component.UserFinder;
import org.sarangchurch.growing.v2.feat.user.infrastructure.component.UserUpdater;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAppender userAppender;
    private final UserFinder userFinder;
    private final UserUpdater userUpdater;

    @Override
    public User register(User user) {
        // 이름 중복 검사 해야하나?
        return userAppender.append(user);
    }

    @Override
    public User findById(Long id) {
        return userFinder.findById(id);
    }

    @Override
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        userUpdater.update(userId, name, phoneNumber, birth, sex, grade);
    }
}
