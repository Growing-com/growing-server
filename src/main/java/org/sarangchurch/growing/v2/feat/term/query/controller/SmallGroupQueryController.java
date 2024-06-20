package org.sarangchurch.growing.v2.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v2.feat.term.query.model.SmallGroup;
import org.sarangchurch.growing.v2.feat.term.query.repository.SmallGroupQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SmallGroupQueryController {
    private final SmallGroupQueryRepository smallGroupQueryRepository;

    @GetMapping("/api/v2/term/{termId}/small-groups")
    public ApiResponse<List<SmallGroup>> findSmallGroups(@PathVariable Long termId) {
        return ApiResponse.of(smallGroupQueryRepository.findAllByTerm(termId));
    }
}
