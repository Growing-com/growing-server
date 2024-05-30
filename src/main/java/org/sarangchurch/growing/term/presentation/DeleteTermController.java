package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.service.DeleteTermService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteTermController {

    private final DeleteTermService deleteTermService;

    @DeleteMapping("/api/term/{termId}")
    public void startTerm(@PathVariable Long termId) {
        deleteTermService.delete(termId);
    }
}
