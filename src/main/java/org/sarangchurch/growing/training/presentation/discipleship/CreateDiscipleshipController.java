package org.sarangchurch.growing.training.presentation.discipleship;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.discipleship.dto.CreateDiscipleshipRequest;
import org.sarangchurch.growing.training.application.discipleship.service.CreateDiscipleshipService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateDiscipleshipController {

    private final CreateDiscipleshipService createDiscipleshipService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/discipleship")
    public void create(@RequestBody @Valid CreateDiscipleshipRequest request) {
        createDiscipleshipService.create(request);
    }
}
