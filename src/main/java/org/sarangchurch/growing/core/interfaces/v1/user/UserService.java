package org.sarangchurch.growing.core.interfaces.v1.user;

import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.user.domain.User;

import java.time.LocalDate;

public interface UserService {
    User register(User user);

    void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade);
}
