package org.sarangchurch.growing.v2.feat.newfamily.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyQueryController {
    private final NewFamilyQueryRepository newFamilyQueryRepository;

    @GetMapping("/api/v2/new-families")
    public ApiResponse<List<NewFamily>> findNewFamilies() {
        return ApiResponse.of(newFamilyQueryRepository.findAll());
    }

    @GetMapping("/api/v2/promoted-new-families")
    public ApiResponse<List<PromotedNewFamily>> findPromotedNewFamilies() {
        return ApiResponse.of(newFamilyQueryRepository.findAllPromotedNewFamily());
    }
}
