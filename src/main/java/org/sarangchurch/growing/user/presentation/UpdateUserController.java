package org.sarangchurch.growing.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.user.application.service.UpdateUserService;
import org.sarangchurch.growing.user.application.dto.UpdateUserRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UpdateUserController {

    private final UpdateUserService updateUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/user/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
        updateUserService.update(userId, request);
    }
}
