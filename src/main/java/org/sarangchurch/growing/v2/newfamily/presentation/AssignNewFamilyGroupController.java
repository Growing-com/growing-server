package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.assignleader.AssignNewFamilyGroupRequest;
import org.sarangchurch.growing.v2.newfamily.application.assignleader.AssignNewFamilyGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignNewFamilyGroupController {

    private final AssignNewFamilyGroupService assignNewFamilyGroupService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/assign-new-family-group")
    public void assignNewFamilyGroup(@PathVariable Long newFamilyId, @RequestBody @Valid AssignNewFamilyGroupRequest request) {
        assignNewFamilyGroupService.assign(newFamilyId, request.getNewFamilyGroupId());
    }
}
