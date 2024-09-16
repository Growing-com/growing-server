package org.sarangchurch.growing.v1.feat.term.query.controller;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.sarangchurch.growing.v1.feat.term.query.model.TermListItem;
import org.sarangchurch.growing.v1.feat.term.query.repository.TermQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("V1TermQueryController")
@RequiredArgsConstructor
public class TermQueryController {
    private final TermQueryRepository termQueryRepository;

    @GetMapping("/api/v1/terms")
    public ApiResponse<List<TermListItem>> findAll() {
        return ApiResponse.of(termQueryRepository.findAll());
    }

    @GetMapping("/api/v1/terms/active-term")
    public TermListItem findActive() {
        return termQueryRepository.findActive();
    }
}
