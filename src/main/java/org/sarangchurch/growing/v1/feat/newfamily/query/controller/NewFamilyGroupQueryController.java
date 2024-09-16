package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.NewFamilyGroupListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.NewFamilyGroupQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyGroupQueryController {
    private final NewFamilyGroupQueryRepository newFamilyGroupQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/new-family-groups")
    public ApiResponse<List<NewFamilyGroupListItem>> findActive(@PathVariable Long termId) {
        return ApiResponse.of(newFamilyGroupQueryRepository.findByTermId(termId));
    }

}
