package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupleader.AssignSmallGroupLeaderRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupleader.AssignSmallGroupLeaderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignSmallGroupLeaderController {
    private final AssignSmallGroupLeaderService assignSmallGroupLeaderService;

    @PostMapping("/api/v1/terms/{termId}/assign-small-group-leader")
    public void assignSmallGroupLeader(@PathVariable Long termId, @RequestBody @Valid AssignSmallGroupLeaderRequest request) {
        assignSmallGroupLeaderService.assign(termId, request.getCodyUserId(), request.getSmallGroupLeaderUserIds());
    }
}
