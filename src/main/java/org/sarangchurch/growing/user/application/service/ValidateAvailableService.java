package org.sarangchurch.growing.user.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.sarangchurch.growing.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidateAvailableService {
    private final UserRepository userRepository;

    public void validateUsernameAvailable(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public void validateNameChangeable(UserEntity user, String nextName) {
        if (user.matchName(nextName)) {
            return;
        }

        validateNameAvailable(nextName);
    }

    public void validateNameAvailable(String name) {
        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }
}
