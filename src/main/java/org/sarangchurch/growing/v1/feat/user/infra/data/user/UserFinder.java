package org.sarangchurch.growing.v1.feat.user.infra.data.user;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.domain.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public User findByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    public List<User> findByIdIn(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public List<User> findByIdInOrThrow(List<Long> ids) {
        List<User> users = userRepository.findByIdIn(ids);

        if (users.size() != ids.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        return users;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    public List<User> findAllActive() {
        return userRepository.findAllActive();
    }
}
