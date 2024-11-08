package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assigncody.AssignCodyRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assigncody.AssignCodyService;
import org.sarangchurch.growing.v1.feat.lineup.application.assignjuniorpastor.AssignJuniorPastorRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignjuniorpastor.AssignJuniorPastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignCodyController {
    private final AssignCodyService assignCodyService;

    @PostMapping("/api/v1/terms/{termId}/assign-cody")
    public void assignCody(@PathVariable Long termId, @RequestBody @Valid AssignCodyRequest request) {
        assignCodyService.assign(termId, request.getUserIds());
    }
}
