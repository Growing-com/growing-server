package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.user.query.model.LineOutUserListItem;
import org.sarangchurch.growing.v1.feat.user.query.repository.LineOutUserQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LineOutUserQueryController {
    private final LineOutUserQueryRepository lineOutUserQueryRepository;

    @GetMapping("/api/v1/line-out-users")
    public ApiResponse<List<LineOutUserListItem>> lineOutUsers() {
        return ApiResponse.of(lineOutUserQueryRepository.findAll());
    }
}
