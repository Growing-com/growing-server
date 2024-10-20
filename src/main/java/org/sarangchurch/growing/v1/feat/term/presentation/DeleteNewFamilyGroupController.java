package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.deletenewfamilygroup.DeleteNewFamilyGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteNewFamilyGroupController {
    private final DeleteNewFamilyGroupService deleteNewFamilyGroupService;

    @PostMapping("/api/v1/new-family-groups/{newFamilyGroupId}/delete")
    public void deleteNewFamilyGroup(@PathVariable Long newFamilyGroupId) {
        deleteNewFamilyGroupService.delete(newFamilyGroupId);
    }
}
