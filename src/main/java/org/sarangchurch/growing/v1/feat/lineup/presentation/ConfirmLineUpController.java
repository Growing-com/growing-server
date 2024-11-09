package org.sarangchurch.growing.v1.feat.lineup.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.application.confirmlineup.ConfirmLineUpService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfirmLineUpController {
    private final ConfirmLineUpService confirmLineUpService;

    @PostMapping("/api/v1/terms/{termId}/confirm-line-up")
    public void confirmLineUp(@PathVariable Long termId) {
        confirmLineUpService.confirm(termId);
    }
}
