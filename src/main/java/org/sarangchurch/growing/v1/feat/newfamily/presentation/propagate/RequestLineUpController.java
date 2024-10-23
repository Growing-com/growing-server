package org.sarangchurch.growing.v1.feat.newfamily.presentation.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.propagate.requestlineup.RequestLineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.propagate.requestlineup.RequestLineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RequestLineUpController {
    private final RequestLineUpService requestLineupService;

    @PostMapping("/api/v1/new-families/request-line-up")
    public void requestLineUp(@RequestBody @Valid RequestLineUpRequest request) {
        requestLineupService.requestLineUp(request);
    }
}
