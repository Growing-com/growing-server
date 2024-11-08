package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamily.AssignNewFamilyRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamily.AssignNewFamilyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignNewFamilyController {
    private final AssignNewFamilyService assignNewFamilyService;

    @PostMapping("/api/v1/terms/{termId}/assign-new-family")
    public void assignNewFamily(@PathVariable Long termId, @RequestBody @Valid AssignNewFamilyRequest request) {
        assignNewFamilyService.assign(termId, request.getLeaderUserId(), request.getNewFamilyIds());
    }
}
