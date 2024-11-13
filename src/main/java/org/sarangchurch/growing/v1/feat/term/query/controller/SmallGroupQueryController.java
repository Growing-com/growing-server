package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.dto.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupWithCodyListItem;
import org.sarangchurch.growing.v1.feat.term.query.model.SmallGroupMemberListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.SmallGroupQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SmallGroupQueryController {
    private final SmallGroupQueryRepository smallGroupQueryRepository;

    @GetMapping("/api/v1/terms/{termId}/small-groups-by-cody")
    public ApiResponse<List<SmallGroupWithCodyListItem>> findSmallGroupsGroupedByCodyByTermId(@PathVariable Long termId) {
        return ApiResponse.of(smallGroupQueryRepository.findByTermId(termId));
    }

    @GetMapping("/api/v1/small-groups/{smallGroupId}/small-group-members")
    public ApiResponse<List<SmallGroupMemberListItem>> findMembersBySmallGroupId(@PathVariable Long smallGroupId) {
        return ApiResponse.of(smallGroupQueryRepository.findMembersBySmallGroupId(smallGroupId));
    }
}
