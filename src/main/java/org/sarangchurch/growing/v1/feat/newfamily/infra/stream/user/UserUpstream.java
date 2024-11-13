package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Sex;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

    public void activateByIdIn(List<Long> ids) {
        userService.activateByIdIn(ids);
    }

    public void deactivateByIdIn(List<Long> ids) {
        userService.deActivateByIdIn(ids);
    }
}
