package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.dto.RegisterTermRequest;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.sarangchurch.growing.term.domain.term.TermStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterTermService {
    private final TermRepository termRepository;

    public void register(RegisterTermRequest request) {
        if (termRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 텀 이름입니다.");
        }

        Term newTerm = Term.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(TermStatus.PENDING)
                .isActive(false)
                .memo(request.getMemo())
                .groupings(request.getGroupings())
                .build();

        termRepository.save(newTerm);
    }
}
