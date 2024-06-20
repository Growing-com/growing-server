package org.sarangchurch.growing.v2.feat.newfamily.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v2.feat.newfamily.query.model.LinedoutNewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.query.repository.LinedoutNewFamilyQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinedoutNewFamilyQueryController {
    private final LinedoutNewFamilyQueryRepository linedoutNewFamilyQueryRepository;

    @GetMapping("/api/v2/lined-out-new-families")
    public ApiResponse<List<LinedoutNewFamily>> findLinedoutNewFamilies() {
        return ApiResponse.of(linedoutNewFamilyQueryRepository.findAll());
    }
}
