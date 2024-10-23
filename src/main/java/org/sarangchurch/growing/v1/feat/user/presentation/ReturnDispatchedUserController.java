package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.returndispatched.ReturnDispatchedService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReturnDispatchedUserController {
    private final ReturnDispatchedService returnDispatchedService;

    @PostMapping("/api/v1/dispatched-users/{dispatchUserId}/return")
    public void returnDispatched(@PathVariable Long dispatchUserId) {
        returnDispatchedService.returnDispatched(dispatchUserId);
    }
}
