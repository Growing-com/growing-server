package org.sarangchurch.growing.v2.auth.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUsername(String username);
}
