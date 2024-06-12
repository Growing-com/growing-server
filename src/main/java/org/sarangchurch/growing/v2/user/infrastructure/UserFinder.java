package org.sarangchurch.growing.v2.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.user.domain.User;
import org.sarangchurch.growing.v2.user.domain.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("지체를 찾을 수 없습니다."));
    }
}
