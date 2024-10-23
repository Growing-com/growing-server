package org.sarangchurch.growing.v1.feat.term.presentation.newfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.createnewfamilygroup.CreateNewFamilyGroupRequest;
import org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.createnewfamilygroup.CreateNewFamilyGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateNewFamilyGroupController {
    private final CreateNewFamilyGroupService createNewFamilyGroupService;

    @PostMapping("/api/v1/terms/{termId}/create-new-family-group")
    public void createNewFamilyGroup(
            @PathVariable Long termId,
            @RequestBody @Valid CreateNewFamilyGroupRequest request
    ) {
        createNewFamilyGroupService.create(termId, request);
    }
}
