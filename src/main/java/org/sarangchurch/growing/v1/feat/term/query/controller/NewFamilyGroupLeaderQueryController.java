package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.NewFamilyGroupLeaderListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.NewFamilyLeaderQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewFamilyGroupLeaderQueryController {
    private final NewFamilyLeaderQueryRepository newFamilyLeaderQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/new-family-group-leaders")
    public ApiResponse<List<NewFamilyGroupLeaderListItem>> findNewFamilyGroupLeaderByTerm(@PathVariable Long termId) {
        return ApiResponse.of(newFamilyLeaderQueryRepository.findByTerm(termId));
    }
}
