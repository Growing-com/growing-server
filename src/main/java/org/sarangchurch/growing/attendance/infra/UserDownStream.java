package org.sarangchurch.growing.attendance.infra;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.attendance.domain.user.User;
import org.sarangchurch.growing.user.application.upstream.UserUpstream;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("Attendance_UserDownStream")
@Transactional
@RequiredArgsConstructor
public class UserDownStream {
    private final UserUpstream userUpstream;

    public User findById(Long id) {
        UserEntity origin = userUpstream.findById(id);

        return new User(origin.getId(), origin.getRole() == Role.ADMIN);
    }
}
