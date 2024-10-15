package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.CodyListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.CodyQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CodyQueryController {
    private final CodyQueryRepository codyQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/codies")
    public ApiResponse<List<CodyListItem>> findCodiesByTerm(@PathVariable Long termId) {
        return ApiResponse.of(codyQueryRepository.findByTerm(termId));
    }
}
