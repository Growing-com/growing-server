package org.sarangchurch.growing.v1.feat.user.infrastructure.jpa;

import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDispatchedUserRepository extends JpaRepository<DispatchedUser, Long>, DispatchedUserRepository {
}
