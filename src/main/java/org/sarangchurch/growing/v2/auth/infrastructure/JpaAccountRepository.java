package org.sarangchurch.growing.v2.auth.infrastructure;

import org.sarangchurch.growing.v2.auth.domain.Account;
import org.sarangchurch.growing.v2.auth.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
