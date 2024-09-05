package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.update.V1UpdateRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.update.V1UpdateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1UpdateNewFamilyController {
    private final V1UpdateService updateService;

    @PostMapping("/api/v1/new-families/{newFamilyId}/update")
    public void updateNewFamily(
            @PathVariable("newFamilyId") Long newFamilyId,
            @RequestBody @Valid V1UpdateRequest request
    ) {
        updateService.update(newFamilyId, request);
    }
}
