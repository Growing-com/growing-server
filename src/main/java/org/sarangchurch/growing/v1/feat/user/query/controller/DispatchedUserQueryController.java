package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.user.query.model.DispatchedUserListItem;
import org.sarangchurch.growing.v1.feat.user.query.repository.DispatchedUserQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DispatchedUserQueryController {
    private final DispatchedUserQueryRepository dispatchedUserQueryRepository;

    @GetMapping("/api/v1/dispatched-users")
    public ApiResponse<List<DispatchedUserListItem>> findAll() {
        return ApiResponse.of(dispatchedUserQueryRepository.findAll());
    }
}
