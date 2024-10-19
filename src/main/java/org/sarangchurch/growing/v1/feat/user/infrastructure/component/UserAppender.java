package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAppender {
    private final UserWriter userWriter;

    public User append(User user) {
        return userWriter.save(user);
    }
}
