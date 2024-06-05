package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.AssignLeaderRequest;
import org.sarangchurch.growing.v2.newfamily.application.AssignLeaderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignLeaderController {

    private final AssignLeaderService assignLeaderService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/assign-new-family-group")
    public void assignLeader(@PathVariable Long newFamilyId, @RequestBody @Valid AssignLeaderRequest request) {
        assignLeaderService.assign(newFamilyId, request.getNewFamilyGroupId());
    }
}
