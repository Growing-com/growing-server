package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.user.query.model.UserListItem;
import org.sarangchurch.growing.v1.feat.user.query.repository.UserQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
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
}
