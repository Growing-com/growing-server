package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.V1LineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.V1LineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1LineUpController {
    private final V1LineUpService service;

    @PostMapping("/api/v1/new-families/line-up")
    public void lineUp(@RequestBody @Valid V1LineUpRequest request) {
        service.lineUp(request);
    }
}
