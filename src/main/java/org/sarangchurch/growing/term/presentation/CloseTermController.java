package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.service.CloseTermService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CloseTermController {

    private final CloseTermService closeTermService;

    @PostMapping("/api/term/{termId}/close")
    public void startTerm(@PathVariable Long termId) {
        closeTermService.close(termId);
    }
}
