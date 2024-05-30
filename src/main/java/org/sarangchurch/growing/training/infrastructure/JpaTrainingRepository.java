package org.sarangchurch.growing.training.infrastructure;

import org.sarangchurch.growing.training.domain.training.Training;
import org.sarangchurch.growing.training.domain.training.TrainingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTrainingRepository extends JpaRepository<Training, Long>, TrainingRepository {
}
