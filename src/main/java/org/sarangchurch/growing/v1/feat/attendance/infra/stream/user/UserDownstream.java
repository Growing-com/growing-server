package org.sarangchurch.growing.v1.feat.attendance.infra.stream.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.springframework.stereotype.Component;

@Component("Attendance_User_Downstream")
@RequiredArgsConstructor
public class UserDownstream {
    private final UserService userService;

    public long countActiveUsers() {
        return userService.countActiveUsers();
    }
}
