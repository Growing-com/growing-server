package org.sarangchurch.growing.v1.feat.user.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    List<User> findByIdIn(List<Long> ids);

    List<User> findAll();

    List<User> findAllActive();

    long countActiveUsers();
}
