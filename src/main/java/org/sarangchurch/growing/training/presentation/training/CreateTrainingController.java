package org.sarangchurch.growing.training.presentation.training;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.training.dto.CreateTrainingRequest;
import org.sarangchurch.growing.training.application.training.service.CreateTrainingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateTrainingController {

    private final CreateTrainingService createTrainingService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/training")
    public void create(@RequestBody @Valid CreateTrainingRequest request) {
        createTrainingService.create(request);
    }
}
