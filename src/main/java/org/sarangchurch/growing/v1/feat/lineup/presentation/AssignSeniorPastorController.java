package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignseniorpastor.AssignSeniorPastorRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignseniorpastor.AssignSeniorPastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignSeniorPastorController {
    private final AssignSeniorPastorService assignSeniorPastorService;

    @PostMapping("/api/v1/terms/{termId}/assign-senior-pastor")
    public void assignSeniorPastor(@PathVariable Long termId, @RequestBody @Valid AssignSeniorPastorRequest request) {
        assignSeniorPastorService.assign(termId, request.getUserId());
    }
}
