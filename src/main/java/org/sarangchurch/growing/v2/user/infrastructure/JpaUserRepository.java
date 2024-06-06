package org.sarangchurch.growing.v2.user.infrastructure;

import org.sarangchurch.growing.v2.user.domain.User;
import org.sarangchurch.growing.v2.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("V2JpaUserRepository")
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
}
