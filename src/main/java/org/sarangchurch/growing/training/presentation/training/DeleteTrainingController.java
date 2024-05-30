package org.sarangchurch.growing.training.presentation.training;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.domain.training.TrainingRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteTrainingController {
    private final TrainingRepository trainingRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/training/{trainingId}")
    public void deleteDiscipleship(@PathVariable Long trainingId) {
        try {
            trainingRepository.deleteById(trainingId);
        } catch (EmptyResultDataAccessException e) {
            // Ignore
        } catch (Exception e) {
            throw e;
        }
    }
}
