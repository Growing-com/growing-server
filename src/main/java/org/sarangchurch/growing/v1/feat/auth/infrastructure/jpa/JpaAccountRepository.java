package org.sarangchurch.growing.v1.feat.auth.infrastructure.jpa;

import org.sarangchurch.growing.v1.feat.user.domain.account.Account;
import org.sarangchurch.growing.v1.feat.user.domain.account.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, AccountRepository {
}
