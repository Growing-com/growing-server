package org.sarangchurch.growing.training.presentation.training;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.training.dto.UpdateTrainingRequest;
import org.sarangchurch.growing.training.application.training.service.UpdateTrainingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateTrainingController {

    private final UpdateTrainingService updateTrainingService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/trainings/{trainingId}")
    public void updateTraining(
            @PathVariable Long trainingId,
            @RequestBody @Valid UpdateTrainingRequest request
    ) {
        updateTrainingService.update(trainingId, request);
    }
}
