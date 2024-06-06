package org.sarangchurch.growing.v2.term.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.sarangchurch.growing.v2.term.domain.SmallGroup;
import org.sarangchurch.growing.v2.term.domain.SmallGroupRepository;
import org.sarangchurch.growing.v2.term.domain.Term;
import org.sarangchurch.growing.v2.term.domain.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TermServiceImpl implements TermService {
    private final SmallGroupRepository smallGroupRepository;
    private final TermRepository termRepository;

    @Override
    public void lineupUser(Long userId, Long smallGroupId) {
        // TODO: 라인업
        log.info("line up user={} to smallGroup={}", userId, smallGroupId);
    }

    @Override
    public Term findTermBySmallGroupId(Long smallGroupId) {
        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        return termRepository.findById(smallGroup.getTermId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }
}
