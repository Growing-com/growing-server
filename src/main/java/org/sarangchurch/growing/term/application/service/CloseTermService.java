package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CloseTermService {
    private final TermRepository termRepository;

    public void close(Long termId) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다"));

        term.close();
    }
}
