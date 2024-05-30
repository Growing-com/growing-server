package org.sarangchurch.growing.user.application.upstream;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.sarangchurch.growing.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpstream {

    private final UserRepository userRepository;

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public void validateIds(List<Long> userIds) {
        List<UserEntity> existingUsers = userRepository.findByIdIn(userIds);

        if (existingUsers.size() != userIds.size()) {
            throw new IllegalArgumentException("유효하지 않은 userId가 포함되어있습니다.");
        }
    }
}
