package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.DutyDistributionCount;
import org.sarangchurch.growing.v1.feat.term.query.repository.DutyDistributionCountQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DutyDistributionCountQueryController {
    private final DutyDistributionCountQueryRepository dutyDistributionCountQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/duty-distribution-count")
    public ApiResponse<DutyDistributionCount> dutyDistributionCount(@PathVariable Long termId) {
        return ApiResponse.of(dutyDistributionCountQueryRepository.findByTerm(termId));
    }
}
