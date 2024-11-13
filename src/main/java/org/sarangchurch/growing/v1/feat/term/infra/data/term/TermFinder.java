package org.sarangchurch.growing.v1.feat.term.infra.data.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermRepository;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TermFinder {
    private final TermRepository termRepository;

    public Term findById(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }

    public Term findActiveByIdOrThrow(Long termId) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));

        if (!term.isActive()) {
            throw new IllegalStateException("텀이 활성 상태가 아닙니다.");
        }

        return term;
    }

    public Term findActiveOrThrow() {
        return termRepository.findActive()
                .orElseThrow(() -> new IllegalArgumentException("활성 텀이 존재하지 않습니다."));
    }

    public Optional<Term> findByStatus(TermStatus termStatus) {
        return termRepository.findByStatus(termStatus);
    }

    public Optional<Term> findLineUpTerm() {
        return termRepository.findByStatus(TermStatus.LINE_UP);
    }
}
