package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserDispatchController {

    @PostMapping("/api/v1/users/dispatch")
    public void dispatch() {
    }
}
