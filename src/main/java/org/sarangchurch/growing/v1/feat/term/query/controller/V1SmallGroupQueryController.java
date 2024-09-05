package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.V1SmallGroupListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.V1SmallGroupQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class V1SmallGroupQueryController {
    private final V1SmallGroupQueryRepository repository;

    @GetMapping("/api/v1/active-small-groups")
    public ApiResponse<List<V1SmallGroupListItem>> findActive() {
        return ApiResponse.of(repository.findAll());
    }
}
