package org.sarangchurch.growing.v1.feat.newfamily.query;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class V1NewFamilyQueryController {

    private final V1NewFamilyQueryRepository repository;

    @GetMapping("/api/v1/new-families")
    public ApiResponse<List<V1NewFamily>> findAll() {
        return ApiResponse.of(repository.findAll());
    }
}
