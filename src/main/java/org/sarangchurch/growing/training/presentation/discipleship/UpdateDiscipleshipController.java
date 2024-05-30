package org.sarangchurch.growing.training.presentation.discipleship;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.discipleship.dto.UpdateDiscipleshipRequest;
import org.sarangchurch.growing.training.application.discipleship.service.UpdateDiscipleshipService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateDiscipleshipController {

    private final UpdateDiscipleshipService updateDiscipleshipService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/discipleship/{discipleshipId}")
    public void updateTraining(
            @PathVariable Long discipleshipId,
            @RequestBody @Valid UpdateDiscipleshipRequest request
    ) {
        updateDiscipleshipService.update(discipleshipId, request);
    }
}
