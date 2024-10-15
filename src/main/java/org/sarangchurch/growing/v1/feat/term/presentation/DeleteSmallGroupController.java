package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.deletesmallgroup.DeleteSmallGroupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSmallGroupController {

    private final DeleteSmallGroupService deleteSmallGroupService;

    @PostMapping("/api/v1/small-groups/{smallGroupId}/delete")
    public void deleteSmallGroup(@PathVariable Long smallGroupId) {
        deleteSmallGroupService.delete(smallGroupId);
    }
}
