package org.sarangchurch.growing.v1.feat.user.presentation.emit;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.emit.lineout.UserLineOutRequest;
import org.sarangchurch.growing.v1.feat.user.application.emit.lineout.UserLineOutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserLineOutController {
    private final UserLineOutService lineOutService;

    @PostMapping("/api/v1/users/line-out")
    public void lineOut(@RequestBody @Valid UserLineOutRequest request) {
        lineOutService.lineOut(request);
    }
}
