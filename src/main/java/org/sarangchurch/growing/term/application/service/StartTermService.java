package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.sarangchurch.growing.term.domain.term.TermStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StartTermService {
    private final TermRepository termRepository;

    public void start(Long termId) {
        boolean activeTermExists = termRepository.existsByStatus(TermStatus.ACTIVE);

        if (activeTermExists) {
            throw new IllegalArgumentException("진행중인 텀이 없어야합니다.");
        }

        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다"));

        term.start();
    }
}
