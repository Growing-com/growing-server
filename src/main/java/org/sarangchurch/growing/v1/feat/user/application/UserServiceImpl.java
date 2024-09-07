package org.sarangchurch.growing.v1.feat.user.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.core.interfaces.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserAppender;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserUpdater;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAppender userAppender;
    private final UserUpdater userUpdater;

    @Override
    public User register(User user) {
        // 이름 중복 검사 해야하나?
        return userAppender.append(user);
    }

    @Override
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        userUpdater.update(userId, name, phoneNumber, birth, sex, grade);
    }
}
