package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.promote.PromoteRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.promote.PromoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PromoteController {
    private final PromoteService service;

    @PostMapping("/api/v1/new-families/promote")
    public void promote(@RequestBody @Valid PromoteRequest request) {
        service.promote(request);
    }
}
