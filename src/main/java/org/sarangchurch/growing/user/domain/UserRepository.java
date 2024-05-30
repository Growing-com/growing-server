package org.sarangchurch.growing.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(Long id);
    UserEntity save(UserEntity user);
    boolean existsByName(String name);
    List<UserEntity> findByIdIn(List<Long> userIds);
}
