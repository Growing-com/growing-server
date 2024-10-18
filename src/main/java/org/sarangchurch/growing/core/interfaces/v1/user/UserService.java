package org.sarangchurch.growing.core.interfaces.v1.user;

import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User register(User user);

    void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade);

    User findById(Long userId);

    List<User> findByIdIn(List<Long> ids);

    void activateByIdIn(List<Long> ids);

    void deActivateByIdIn(List<Long> ids);
}
