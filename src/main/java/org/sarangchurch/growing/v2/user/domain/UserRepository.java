package org.sarangchurch.growing.v2.user.domain;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);
}
