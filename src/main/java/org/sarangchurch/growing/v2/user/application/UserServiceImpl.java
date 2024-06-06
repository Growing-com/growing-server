package org.sarangchurch.growing.v2.user.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.user.UserService;
import org.sarangchurch.growing.v2.user.domain.User;
import org.sarangchurch.growing.v2.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User register(User user) {
        // 이름 중복 검사 해야하나?
        return userRepository.save(user);
    }
}
