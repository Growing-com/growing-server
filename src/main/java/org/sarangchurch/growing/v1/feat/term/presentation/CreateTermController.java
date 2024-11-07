package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.createterm.CreateTermRequest;
import org.sarangchurch.growing.v1.feat.term.application.createterm.CreateTermService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CreateTermController {
    private final CreateTermService createTermService;

    @PostMapping("/api/v1/terms/create")
    public void createTerm(@RequestBody @Valid CreateTermRequest request) {
        createTermService.create(request);
    }
}
