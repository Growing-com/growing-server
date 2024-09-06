package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup.V1TemporaryLineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup.V1TemporaryLineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1TemporaryLineUpController {
    private final V1TemporaryLineUpService service;

    @PostMapping("/api/v1/new-families/temporary-line-up")
    public void temporaryLineUp(@RequestBody @Valid V1TemporaryLineUpRequest request) {
        service.temporaryLineUp(request);
    }
}
