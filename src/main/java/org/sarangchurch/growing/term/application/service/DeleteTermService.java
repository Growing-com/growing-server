package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteTermService {
    private final TermRepository termRepository;

    public void delete(Long termId) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다"));

        // 텀 삭제 제약 조건 추가될 수 있음.
        if (!term.isPending()) {
            throw new IllegalStateException("대기중인 텀만 삭제할 수 있습니다.");
        }

        // 자식 테이블의 레코드들 삭제

        termRepository.deleteById(termId);
    }
}
