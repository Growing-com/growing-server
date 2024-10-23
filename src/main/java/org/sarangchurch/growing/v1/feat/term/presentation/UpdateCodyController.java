package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.updatecody.UpdateCodyRequest;
import org.sarangchurch.growing.v1.feat.term.application.updatecody.UpdateCodyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateCodyController {
    private final UpdateCodyService updateCodyService;

    @PostMapping("/api/v1/codies/{codyId}/update")
    public void updateCody(@PathVariable Long codyId, @RequestBody @Valid UpdateCodyRequest request) {
        updateCodyService.update(codyId, request);
    }
}
