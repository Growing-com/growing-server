package org.sarangchurch.growing.v2.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.query.repository.NewFamilyQueryRepository;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.PromotedNewFamily;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyQueryController {
    private final NewFamilyQueryRepository newFamilyQueryRepository;

    @GetMapping("/api/v2/new-families")
    public ApiResponse<List<NewFamily>> findNewFamilies(@RequestParam(required = false) Long newFamilyGroupId) {
        if (newFamilyGroupId != null) {
            return ApiResponse.of(newFamilyQueryRepository.findByNewFamilyGroup(newFamilyGroupId));
        }

        return ApiResponse.of(newFamilyQueryRepository.findAll());
    }

    @GetMapping("/api/v2/new-families/{newFamilyId}")
    public ApiResponse<NewFamily> findNewFamily(@PathVariable Long newFamilyId) {
        return ApiResponse.of(newFamilyQueryRepository.findById(newFamilyId));
    }

    @GetMapping("/api/v2/promoted-new-families")
    public ApiResponse<List<PromotedNewFamily>> findPromotedNewFamilies() {
        return ApiResponse.of(newFamilyQueryRepository.findAllPromotedNewFamily());
    }
}
