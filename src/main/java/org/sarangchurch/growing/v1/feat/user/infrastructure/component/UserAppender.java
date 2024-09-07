package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.sarangchurch.growing.v1.feat.user.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserAppender {
    private final UserRepository userRepository;

    @Transactional
    public User append(User user) {
        return userRepository.save(user);
    }
}
