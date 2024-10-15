package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.createsmallgroup.CreateSmallGroupRequest;
import org.sarangchurch.growing.v1.feat.term.application.createsmallgroup.CreateSmallGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateSmallGroupController {

    private final CreateSmallGroupService createSmallGroupService;

    @PostMapping("/api/v1/terms/{termId}/create-small-group")
    public void createSmallGroup(
            @PathVariable Long termId,
            @RequestBody @Valid CreateSmallGroupRequest request
    ) {
        createSmallGroupService.create(termId, request);
    }
}
