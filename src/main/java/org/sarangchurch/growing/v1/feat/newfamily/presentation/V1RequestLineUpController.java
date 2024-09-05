package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.requestlineup.V1RequestLineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.requestlineup.V1RequestLineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1RequestLineUpController {
    private final V1RequestLineUpService requestLineupService;

    @PostMapping("/api/v1/new-families/request-line-up")
    public void requestLineUp(@RequestBody @Valid V1RequestLineUpRequest request) {
        requestLineupService.requestLineUp(request);
    }
}
