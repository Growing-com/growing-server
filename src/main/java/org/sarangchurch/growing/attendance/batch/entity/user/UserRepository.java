package org.sarangchurch.growing.attendance.batch.entity.user;

import org.sarangchurch.growing.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    long countByVisitDate(LocalDate visitDate);
}
