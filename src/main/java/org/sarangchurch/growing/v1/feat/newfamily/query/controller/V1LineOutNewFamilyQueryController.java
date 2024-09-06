package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1LineOutNewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.V1LineOutNewFamilyQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class V1LineOutNewFamilyQueryController {
    private final V1LineOutNewFamilyQueryRepository lineOutNewFamilyQueryRepository;

    @GetMapping("/api/v1/line-out-new-families")
    public ApiResponse<List<V1LineOutNewFamilyListItem>> lineOutNewFamilies() {
        return ApiResponse.of(lineOutNewFamilyQueryRepository.findAll());
    }
}
