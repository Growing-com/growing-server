package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.update.UserUpdateRequest;
import org.sarangchurch.growing.v1.feat.user.application.update.UserUpdateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserUpdateController {
    private final UserUpdateService userUpdateService;

    @PostMapping("/api/v1/users/{userId}/update")
    public void updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        userUpdateService.update(userId, request);
    }
}
