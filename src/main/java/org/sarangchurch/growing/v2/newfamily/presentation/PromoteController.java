package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.PromoteRequest;
import org.sarangchurch.growing.v2.newfamily.application.PromoteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PromoteController {

    private final PromoteService promoteService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/promote")
    public void promote(@PathVariable Long newFamilyId, @RequestBody @Valid PromoteRequest request) {
        promoteService.promote(newFamilyId, request);
    }
}