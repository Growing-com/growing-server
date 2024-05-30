package org.sarangchurch.growing.training.domain.discipleship;

import java.util.Optional;

public interface DiscipleshipRepository {
    Optional<Discipleship> findById(Long id);
    Discipleship save(Discipleship training);
    void deleteById(Long id);
}
