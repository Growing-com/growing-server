package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.DeleteSmallGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FooBarController {

    private final DeleteSmallGroupService deleteSmallGroupService;

    @GetMapping("/api/v1/foobar")
    public void createSmallGroup(
            @RequestParam Long smallGroupId
    ) {
        deleteSmallGroupService.delete(smallGroupId);
    }
}
