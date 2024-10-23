package org.sarangchurch.growing.v1.feat.term.presentation.pastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.pastor.switchseniorpastor.SwitchSeniorPastorRequest;
import org.sarangchurch.growing.v1.feat.term.application.pastor.switchseniorpastor.SwitchSeniorPastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SwitchSeniorPastorController {
    private final SwitchSeniorPastorService switchSeniorPastorService;

    @PostMapping("/api/v1/terms/{termId}/switch-senior-pastor")
    public void switchSeniorPastor(
            @PathVariable Long termId,
            @RequestBody @Valid SwitchSeniorPastorRequest request
    ) {
        switchSeniorPastorService.run(termId, request.getTargetSeniorPastorId());
    }
}
