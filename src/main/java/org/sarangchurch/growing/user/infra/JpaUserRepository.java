package org.sarangchurch.growing.user.infra;

import org.sarangchurch.growing.user.domain.UserEntity;
import org.sarangchurch.growing.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, UserRepository {
}
