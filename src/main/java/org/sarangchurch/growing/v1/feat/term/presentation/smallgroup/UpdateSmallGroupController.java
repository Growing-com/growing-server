package org.sarangchurch.growing.v1.feat.term.presentation.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.smallgroup.updatesmallgroup.UpdateSmallGroupRequest;
import org.sarangchurch.growing.v1.feat.term.application.smallgroup.updatesmallgroup.UpdateSmallGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateSmallGroupController {
    private final UpdateSmallGroupService updateSmallGroupService;

    @PostMapping("/api/v1/small-groups/{smallGroupId}/update")
    public void updateSmallGroup(
            @PathVariable Long smallGroupId,
            @RequestBody @Valid UpdateSmallGroupRequest request
    ) {
        updateSmallGroupService.update(smallGroupId, request);
    }
}
