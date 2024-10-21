package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.register.UserRegisterRequest;
import org.sarangchurch.growing.v1.feat.user.application.register.UserRegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    @PostMapping("/api/v1/users/register")
    public void register(@RequestBody @Valid UserRegisterRequest request) {
        userRegisterService.register(request);
    }
}
