package org.sarangchurch.growing.v1.feat.user.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infra.component.UserUpdater;
import org.sarangchurch.growing.v1.feat.user.infra.data.user.UserFinder;
import org.sarangchurch.growing.v1.feat.user.infra.data.user.UserWriter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserWriter userWriter;
    private final UserUpdater userUpdater;
    private final UserFinder userFinder;

    @Override
    public User register(User user) {
        return userWriter.save(user);
    }

    @Override
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        userUpdater.update(userId, name, phoneNumber, birth, sex, grade);
    }

    @Override
    public User findById(Long userId) {
        return userFinder.findByIdOrThrow(userId);
    }

    @Override
    public List<User> findByIdIn(List<Long> ids) {
        return userFinder.findByIdIn(ids);
    }

    @Override
    public void activateByIdIn(List<Long> ids) {
        List<User> users = userFinder.findByIdIn(ids);

        userUpdater.activate(users);
    }

    @Override
    public void deActivateByIdIn(List<Long> ids) {
        List<User> users = userFinder.findByIdIn(ids);

        userUpdater.deactivate(users);
    }

    @Override
    public List<User> findAll() {
        return userFinder.findAll();
    }

    @Override
    public long countActiveUsers() {
        return userFinder.countActiveUsers();
    }

    @Override
    public List<User> findAllActive() {
        return userFinder.findAllActive();
    }
}
