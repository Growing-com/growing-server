package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamilyGroupListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.V1NewFamilyGroupRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class V1NewFamilyGroupController {
    private final V1NewFamilyGroupRepository repository;

    @GetMapping("/api/v1/active-new-family-groups")
    public ApiResponse<List<V1NewFamilyGroupListItem>> findActive() {
        return ApiResponse.of(repository.findActive());
    }

}
