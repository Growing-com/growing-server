package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.TreeMemberListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.TreeMemberQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TreeMemberQueryController {
    private final TreeMemberQueryRepository treeMemberQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/codies/{codyId}/members")
    public ApiResponse<List<TreeMemberListItem>> findTreeMembers(
            @PathVariable Long termId,
            @PathVariable Long codyId,
            @RequestParam(required = false) Long smallGroupId
    ) {
        // 순모임 소속
        if (smallGroupId != null) {
            return ApiResponse.of(treeMemberQueryRepository.findBySmallGroup(smallGroupId));
        }

        // 새가족반 소속?

        // 전체
        return ApiResponse.of(treeMemberQueryRepository.findByCody(codyId));
    }
}
