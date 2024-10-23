package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.createpastor.CreatePastorRequest;
import org.sarangchurch.growing.v1.feat.term.application.createpastor.CreatePastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreatePastorController {
    private final CreatePastorService createPastorService;

    @PostMapping("/api/v1/terms/{termId}/create-pastor")
    public void createPastor(@PathVariable Long termId, @RequestBody @Valid CreatePastorRequest request) {
        createPastorService.create(termId, request);
    }
}
