package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.V1LineOutRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.V1LineOutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1LineOutController {
    private final V1LineOutService service;

    @PostMapping("/api/v1/new-families/line-out")
    public void lineOut(@RequestBody @Valid V1LineOutRequest request) {
        service.lineOut(request);
    }
}
