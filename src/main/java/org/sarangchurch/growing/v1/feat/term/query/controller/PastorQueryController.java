package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.PastorListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.PastorQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PastorQueryController {
    private final PastorQueryRepository pastorQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/pastors")
    public ApiResponse<List<PastorListItem>> findPastorsByTerm(@PathVariable Long termId) {
        return ApiResponse.of(pastorQueryRepository.findByTerm(termId));
    }
}
