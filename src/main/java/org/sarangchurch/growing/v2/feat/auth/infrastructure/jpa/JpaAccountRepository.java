package org.sarangchurch.growing.v2.feat.auth.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.auth.domain.Account;
import org.sarangchurch.growing.v2.feat.auth.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
