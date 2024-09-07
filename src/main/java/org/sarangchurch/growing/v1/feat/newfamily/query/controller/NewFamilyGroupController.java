package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyGroupListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.NewFamilyGroupRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyGroupController {
    private final NewFamilyGroupRepository repository;

    @GetMapping("/api/v1/active-new-family-groups")
    public ApiResponse<List<NewFamilyGroupListItem>> findActive() {
        return ApiResponse.of(repository.findActive());
    }

}
