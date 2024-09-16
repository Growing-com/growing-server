package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.*;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.NewFamilyQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyQueryController {

    private final NewFamilyQueryRepository repository;

    @GetMapping("/api/v1/new-families")
    public ApiResponse<List<NewFamilyListItem>> findAll() {
        return ApiResponse.of(repository.findAll());
    }

    @GetMapping("/api/v1/new-families/{newFamilyId}")
    public NewFamily findById(@PathVariable("newFamilyId") Long newFamilyId) {
        return repository.findById(newFamilyId);
    }

    @GetMapping("/api/v1/promote-candidate-new-families")
    public ApiResponse<List<LineUpReadyNewFamilyListItem>> promoteCandidateNewFamilies() {
        return ApiResponse.of(repository.findAllPromoteCandidates());
    }

    @GetMapping("/api/v1/promoted-new-families")
    public ApiResponse<List<PromotedNewFamilyListItem>> promotedNewFamilies() {
        return ApiResponse.of(repository.findAllPromoted());
    }

    @GetMapping("/api/v1/temporary-lined-up-new-families")
    public ApiResponse<List<TemporaryLinedUpNewFamilyListItem>> temporaryLinedUpNewFamilies() {
        return ApiResponse.of(repository.findAllTemporaryLinedUp());
    }
}
