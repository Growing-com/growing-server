package org.sarangchurch.growing.v1.feat.user.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.graduate.GraduateRequest;
import org.sarangchurch.growing.v1.feat.user.application.graduate.GraduateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserGraduateController {
    private final GraduateService graduateService;

    @PostMapping("/api/v1/users/graduate")
    public void graduate(@RequestBody @Valid GraduateRequest request) {
        graduateService.graduate(request);
    }
}
