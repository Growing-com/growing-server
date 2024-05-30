package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.service.StartTermService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartTermController {

    private final StartTermService startTermService;

    @PostMapping("/api/term/{termId}/start")
    public void startTerm(@PathVariable Long termId) {
        startTermService.start(termId);
    }
}
