package org.sarangchurch.growing.v1.feat.user.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUsername(String username);
}
