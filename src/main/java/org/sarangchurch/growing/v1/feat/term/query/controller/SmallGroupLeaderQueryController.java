package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;

import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupLeaderListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.SmallGroupLeaderQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SmallGroupLeaderQueryController {
    private final SmallGroupLeaderQueryRepository smallGroupLeaderQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/small-group-leaders")
    public ApiResponse<List<SmallGroupLeaderListItem>> findSmallGroupLeadersByTerm(@PathVariable Long termId) {
        return ApiResponse.of(smallGroupLeaderQueryRepository.findByTerm(termId));
    }
}
