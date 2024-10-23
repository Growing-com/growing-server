package org.sarangchurch.growing.v1.feat.newfamily.presentation.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.propagate.temporarylineup.TemporaryLineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.propagate.temporarylineup.TemporaryLineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TemporaryLineUpController {
    private final TemporaryLineUpService service;

    @PostMapping("/api/v1/new-families/temporary-line-up")
    public void temporaryLineUp(@RequestBody @Valid TemporaryLineUpRequest request) {
        service.temporaryLineUp(request);
    }
}
