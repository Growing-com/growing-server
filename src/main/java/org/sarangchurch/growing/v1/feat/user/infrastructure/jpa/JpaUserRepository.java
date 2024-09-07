package org.sarangchurch.growing.v1.feat.user.infrastructure.jpa;

import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.sarangchurch.growing.v1.feat.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("V2JpaUserRepository")
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
