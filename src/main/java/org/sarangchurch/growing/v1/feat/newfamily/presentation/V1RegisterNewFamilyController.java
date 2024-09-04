package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.register.V1RegisterRequest;
import org.sarangchurch.growing.v1.feat.newfamily.application.register.V1RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class V1RegisterNewFamilyController {

    private final V1RegisterService registerService;

    // TODO: 권한 관리
    @PostMapping("/api/v1/new-families/register")
    public void registerNewFamily(@RequestBody @Valid V1RegisterRequest request) {
        registerService.register(request);
    }
}
