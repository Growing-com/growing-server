package org.sarangchurch.growing.v1.feat.user.infrastructure.jpa;

import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLineOutUserRepository extends JpaRepository<LineOutUser, Long>, LineOutUserRepository {
}
