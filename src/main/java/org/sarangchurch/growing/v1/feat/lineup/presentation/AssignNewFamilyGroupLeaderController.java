package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupleader.AssignNewFamilyGroupLeaderRequest;
import org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupleader.AssignNewFamilyGroupLeaderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AssignNewFamilyGroupLeaderController {
    private final AssignNewFamilyGroupLeaderService assignNewFamilyGroupLeaderService;

    @PostMapping("/api/v1/terms/{termId}/assign-new-family-group-leader")
    public void assignNewFamilyGroupLeader(@PathVariable Long termId, @RequestBody @Valid AssignNewFamilyGroupLeaderRequest request) {
        assignNewFamilyGroupLeaderService.assign(termId, request.getCodyUserId(), request.getNewFamilyGroupLeaderUserIds());
    }
}
