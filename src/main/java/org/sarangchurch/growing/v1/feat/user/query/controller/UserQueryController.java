package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.user.query.model.User;
import org.sarangchurch.growing.v1.feat.user.query.model.UserListItem;
import org.sarangchurch.growing.v1.feat.user.query.repository.UserQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQueryRepository userQueryRepository;

    @GetMapping("/api/v1/users")
    public ApiResponse<List<UserListItem>> users() {
        return ApiResponse.of(userQueryRepository.findAll());
    }

    @GetMapping("/api/v1/users/{userId}")
    public ApiResponse<User> user(@PathVariable Long userId) {
        return ApiResponse.of(userQueryRepository.findById(userId));
    }

    @GetMapping("/")
    public String healthCheck() {
        return "health check";
    }
}
