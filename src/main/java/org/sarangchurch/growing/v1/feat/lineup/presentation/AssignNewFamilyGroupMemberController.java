package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupmember.AssignNewFamilyGroupMemberRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupmember.AssignNewFamilyGroupMemberService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignNewFamilyGroupMemberController {
    private final AssignNewFamilyGroupMemberService assignNewFamilyGroupMemberService;

    @PostMapping("/api/v1/terms/{termId}/assign-new-family-group-member")
    public void assignNewFamilyGroupMember(@PathVariable Long termId, @RequestBody @Valid AssignNewFamilyGroupMemberRequest request) {
        assignNewFamilyGroupMemberService.assign(termId, request.getLeaderUserId(), request.getMemberUserIds());
    }
}
