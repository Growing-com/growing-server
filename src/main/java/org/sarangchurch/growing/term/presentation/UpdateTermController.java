package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.dto.UpdateTermRequest;
import org.sarangchurch.growing.term.application.service.UpdateTermService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateTermController {

    private final UpdateTermService updateTermService;

    @PutMapping("/api/term/{termId}")
    public void updateTerm(
            @RequestBody @Valid UpdateTermRequest request,
            @PathVariable Long termId
    ) {
        updateTermService.update(termId, request);
    }
}
