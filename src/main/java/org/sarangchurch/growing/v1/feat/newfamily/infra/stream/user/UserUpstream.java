package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.core.interfaces.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("NewFamily_User_Upstream")
@RequiredArgsConstructor
public class UserUpstream {
    private final UserService userService;

    public User register(User user) {
        return userService.register(user);
    }

    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        userService.update(userId, name, phoneNumber, birth, sex, grade);
    }
}