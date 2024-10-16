package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.createcody.CreateCodyRequest;
import org.sarangchurch.growing.v1.feat.term.application.createcody.CreateCodyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateCodyController {
    private final CreateCodyService createCodyService;

    @PostMapping("/api/v1/terms/{termId}/create-cody")
    public void createCody(@PathVariable Long termId, @RequestBody @Valid CreateCodyRequest request) {
        createCodyService.create(termId, request);
    }
}
