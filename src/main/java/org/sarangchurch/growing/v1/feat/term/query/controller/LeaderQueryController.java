package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.LeaderListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.LeaderQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeaderQueryController {
    private final LeaderQueryRepository leaderQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/codies")
    public ApiResponse<List<LeaderListItem>> findCodiesByTerm(@PathVariable Long termId) {
        return ApiResponse.of(leaderQueryRepository.findCodiesByTerm(termId));
    }

    @GetMapping("/api/v1/terms/{termId}/new-family-group-leaders")
    public ApiResponse<List<LeaderListItem>> findNewFamilyGroupLeaderByTerm(@PathVariable Long termId) {
        return ApiResponse.of(leaderQueryRepository.findNewFamilyLeadersByTerm(termId));
    }

    @GetMapping("/api/v1/terms/{termId}/small-group-leaders")
    public ApiResponse<List<LeaderListItem>> findSmallGroupLeadersByTerm(@PathVariable Long termId) {
        return ApiResponse.of(leaderQueryRepository.findNewFamilyLeadersByTerm(termId));
    }
}
