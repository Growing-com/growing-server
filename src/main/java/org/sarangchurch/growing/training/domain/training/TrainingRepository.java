package org.sarangchurch.growing.training.domain.training;

import java.util.Optional;

public interface TrainingRepository {
    Optional<Training> findById(Long id);
    Training save(Training training);
    void deleteById(Long id);
}
