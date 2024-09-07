package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.LineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.LineUpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LineUpController {
    private final LineUpService service;

    @PostMapping("/api/v1/new-families/line-up")
    public void lineUp(@RequestBody @Valid LineUpRequest request) {
        service.lineUp(request);
    }
}
