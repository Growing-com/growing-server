package org.sarangchurch.growing.v1.feat.user.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserAppender;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserFinder;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserUpdater;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAppender userAppender;
    private final UserUpdater userUpdater;
    private final UserFinder userFinder;

    @Override
    public User register(User user) {
        // 이름 중복 검사 해야하나?
        return userAppender.append(user);
    }

    @Override
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        userUpdater.update(userId, name, phoneNumber, birth, sex, grade);
    }

    @Override
    public User findById(Long userId) {
        return userFinder.findById(userId);
    }

    @Override
    public List<User> findByIdIn(List<Long> ids) {
        return userFinder.findByIdIn(ids);
    }
}
