package org.sarangchurch.growing.v2.term.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.term.domain.SmallGroup;
import org.sarangchurch.growing.v2.term.domain.SmallGroupRepository;
import org.sarangchurch.growing.v2.term.domain.Term;
import org.sarangchurch.growing.v2.term.domain.TermRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermFinder {
    private final SmallGroupRepository smallGroupRepository;
    private final TermRepository termRepository;

    public Term findBySmallGroupId(Long smallGroupId) {
        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        return termRepository.findById(smallGroup.getTermId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }

    public Term findById(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }
}
