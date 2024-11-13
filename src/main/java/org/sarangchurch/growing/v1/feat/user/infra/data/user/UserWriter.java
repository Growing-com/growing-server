package org.sarangchurch.growing.v1.feat.user.infra.data.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.domain.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWriter {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
