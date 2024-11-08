package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupmember.AssignSmallGroupMemberRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupmember.AssignSmallGroupMemberService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignSmallGroupMemberController {
    private final AssignSmallGroupMemberService smallGroupMemberService;

    @PostMapping("/api/v1/terms/{termId}/assign-small-group-member")
    public void assignSmallGroupMember(@PathVariable Long termId, @RequestBody @Valid AssignSmallGroupMemberRequest request) {
        smallGroupMemberService.assign(termId, request.getLeaderUserId(), request.getMemberUserIds());
    }
}
