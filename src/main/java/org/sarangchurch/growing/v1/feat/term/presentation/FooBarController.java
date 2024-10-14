package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.CreateSmallGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FooBarController {

    private final CreateSmallGroupService createSmallGroupService;

    @GetMapping("/api/v1/foobar")
    public void createSmallGroup(
            @RequestParam Long termId,
            @RequestParam Long codyId,
            @RequestParam Long leaderUserId
    ) {
//        Long termId = 1L;
//        Long codyId = 1L;
//        Long leaderUserId = 1L;

        createSmallGroupService.create(termId, codyId, leaderUserId);
    }
}
