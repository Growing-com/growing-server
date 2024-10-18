package org.sarangchurch.growing.v1.feat.user.infrastructure.jpa;

import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduateUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGraduatedUserRepository extends JpaRepository<GraduatedUser, Long>, GraduateUserRepository {
}
