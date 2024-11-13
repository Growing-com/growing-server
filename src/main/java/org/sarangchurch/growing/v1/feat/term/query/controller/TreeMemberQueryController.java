package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.CodyListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.GroupListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.TreeMemberListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.TreeMemberQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TreeMemberQueryController {
    private final TreeMemberQueryRepository treeMemberQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/codies")
    public ApiResponse<List<CodyListItem>> findCodiesByTermId(@PathVariable Long termId) {
        return ApiResponse.of(treeMemberQueryRepository.findCodiesByTerm(termId));
    }

    @GetMapping("/api/v1/codies/{codyId}/groups")
    public ApiResponse<List<GroupListItem>> findGroupListItem(@PathVariable Long codyId) {
        return ApiResponse.of(treeMemberQueryRepository.findGroupListByCody(codyId));
    }

    @GetMapping("/api/v1/codies/{codyId}/members")
    public ApiResponse<List<TreeMemberListItem>> findTreeMembers(@PathVariable Long codyId) {
        return ApiResponse.of(treeMemberQueryRepository.findByCody(codyId));
    }
}
