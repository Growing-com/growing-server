package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.RegisterNewFamilyRequest;
import org.sarangchurch.growing.v2.newfamily.application.RegisterNewFamilyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyController {

    private final RegisterNewFamilyService registerNewFamilyService;

    // TODO: 권한 관리
    @PostMapping("/api/v2/new-families/register")
    public void registerNewFamily(@RequestBody @Valid RegisterNewFamilyRequest request) {
        registerNewFamilyService.register(request);
    }
}
