package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.linein.UserLineInService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserLineInController {
    private final UserLineInService userLineInService;

    @PostMapping("/api/v1/line-out-users/{lineOutUserId}/line-in")
    public void lineIn(@PathVariable Long lineOutUserId) {
        userLineInService.lineIn(lineOutUserId);
    }
}
