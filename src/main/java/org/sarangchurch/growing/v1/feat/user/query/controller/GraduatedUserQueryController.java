package org.sarangchurch.growing.v1.feat.user.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.user.query.model.GraduatedUserListItem;
import org.sarangchurch.growing.v1.feat.user.query.repository.GraduatedUserQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GraduatedUserQueryController {
    private final GraduatedUserQueryRepository graduatedUserQueryRepository;

    @GetMapping("/api/v1/graduated-users")
    public ApiResponse<List<GraduatedUserListItem>> findAll() {
        return ApiResponse.of(graduatedUserQueryRepository.findAll());
    }
}
