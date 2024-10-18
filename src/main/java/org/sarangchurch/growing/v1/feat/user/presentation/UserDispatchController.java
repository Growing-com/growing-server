package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.dispatch.DispatchRequest;
import org.sarangchurch.growing.v1.feat.user.application.dispatch.DispatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserDispatchController {
    private final DispatchService dispatchService;

    @PostMapping("/api/v1/users/dispatch")
    public void dispatch(@RequestBody @Valid DispatchRequest request) {
        dispatchService.dispatch(request);
    }
}
