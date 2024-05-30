package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterNewFamilyController {

    // TODO: 권한 관리
    @PostMapping("/api/v2/new-families/register")
    public void registerNewFamily(
            @RequestBody @Valid RegisterNewFamilyRequest request
    ) {
        throw new RuntimeException();
    }
}
