package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermFinder {
    private final TermRepository termRepository;

    public Term findById(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }
}
