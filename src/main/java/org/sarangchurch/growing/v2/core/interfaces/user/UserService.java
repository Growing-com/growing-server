package org.sarangchurch.growing.v2.core.interfaces.user;

import org.sarangchurch.growing.v2.core.interfaces.common.Gender;
import org.sarangchurch.growing.v2.feat.user.domain.User;

import java.time.LocalDate;

public interface UserService {
    User register(User user);

    User findById(Long id);

    void update(Long userId, String name, String phoneNumber, LocalDate birth, Gender gender, Integer grade);
}
