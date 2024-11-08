package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignjuniorpastor.AssignJuniorPastorRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignjuniorpastor.AssignJuniorPastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignJuniorPastorController {
    private final AssignJuniorPastorService assignJuniorPastorService;

    @PostMapping("/api/v1/terms/{termId}/assign-junior-pastor")
    public void assignJuniorPastor(@PathVariable Long termId, @RequestBody @Valid AssignJuniorPastorRequest request) {
        assignJuniorPastorService.assign(termId, request.getUserIds());
    }
}
