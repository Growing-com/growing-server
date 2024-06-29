package org.sarangchurch.growing.v2.core.interfaces.user;

import org.sarangchurch.growing.v2.core.interfaces.common.Sex;
import org.sarangchurch.growing.v2.feat.user.domain.User;

import java.time.LocalDate;

public interface UserService {
    User register(User user);

    User findById(Long id);

    void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade);
}
