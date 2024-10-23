package org.sarangchurch.growing.v1.feat.term.presentation.newfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.updatenewfamilygroup.UpdateNewFamilyGroupRequest;
import org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.updatenewfamilygroup.UpdateNewFamilyGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateNewFamilyGroupController {
    private final UpdateNewFamilyGroupService updateNewFamilyGroupService;

    @PostMapping("/api/v1/new-family-groups/{newFamilyGroupId}/update")
    public void updateNewFamilyGroup(
            @PathVariable Long newFamilyGroupId,
            @RequestBody @Valid UpdateNewFamilyGroupRequest request
    ) {
        updateNewFamilyGroupService.update(newFamilyGroupId, request);
    }
}
