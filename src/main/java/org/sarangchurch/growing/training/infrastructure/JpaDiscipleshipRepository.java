package org.sarangchurch.growing.training.infrastructure;

import org.sarangchurch.growing.training.domain.discipleship.Discipleship;
import org.sarangchurch.growing.training.domain.discipleship.DiscipleshipRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDiscipleshipRepository extends JpaRepository<Discipleship, Long>, DiscipleshipRepository {
}
