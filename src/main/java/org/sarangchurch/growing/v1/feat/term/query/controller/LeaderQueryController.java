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

    @GetMapping("/api/v1/terms/{termId}/all-leaders")
    public ApiResponse<List<LeaderListItem>> findAll(@PathVariable Long termId) {
        return ApiResponse.of(leaderQueryRepository.findAllByTerm(termId));
    }
}
