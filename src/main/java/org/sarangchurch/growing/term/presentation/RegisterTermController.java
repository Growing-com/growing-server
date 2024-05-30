package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.dto.RegisterTermRequest;
import org.sarangchurch.growing.term.application.service.RegisterTermService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterTermController {

    private final RegisterTermService registerTermService;

    @PostMapping("/api/term")
    public void registerTerm(@RequestBody @Valid RegisterTermRequest request) {
        registerTermService.register(request);
    }
}
