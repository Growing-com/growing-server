package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.usergradeupdate.UserGradeUpdateRequest;
import org.sarangchurch.growing.v1.feat.user.application.usergradeupdate.UserGradeUpdateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserGradeUpdateController {
    private final UserGradeUpdateService userGradeUpdateService;

    @PostMapping("/api/v1/user-grade-update")
    public void userGradeUpdate(@RequestBody @Valid UserGradeUpdateRequest request) {
        userGradeUpdateService.update(request);
    }
}
