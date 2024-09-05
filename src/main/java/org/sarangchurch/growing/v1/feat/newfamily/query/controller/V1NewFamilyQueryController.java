package org.sarangchurch.growing.v1.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1LineUpReadyNewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.query.model.V1NewFamilyListItem;
import org.sarangchurch.growing.v1.feat.newfamily.query.repository.V1NewFamilyQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class V1NewFamilyQueryController {

    private final V1NewFamilyQueryRepository repository;

    @GetMapping("/api/v1/new-families")
    public ApiResponse<List<V1NewFamilyListItem>> findAll() {
        return ApiResponse.of(repository.findAll());
    }

    @GetMapping("/api/v1/new-families/{newFamilyId}")
    public V1NewFamily findById(@PathVariable("newFamilyId") Long newFamilyId) {
        return repository.findById(newFamilyId);
    }

    @GetMapping("/api/v1/line-up-ready-new-families")
    public ApiResponse<List<V1LineUpReadyNewFamilyListItem>> findLineUpReadyNewFamilies() {
        return ApiResponse.of(repository.findAllLineUpReady());
    }
}
