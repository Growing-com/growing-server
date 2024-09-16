package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.LineOutRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.LineOutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LineOutController {
    private final LineOutService service;

    @PostMapping("/api/v1/new-families/line-out")
    public void lineOut(@RequestBody @Valid LineOutRequest request) {
        service.lineOut(request);
    }
}
