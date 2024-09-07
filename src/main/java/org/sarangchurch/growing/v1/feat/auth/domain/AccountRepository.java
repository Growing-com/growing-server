package org.sarangchurch.growing.v1.feat.auth.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUsername(String username);
}
