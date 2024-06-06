package org.sarangchurch.growing.v2.term.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.sarangchurch.growing.v2.term.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TermServiceImpl implements TermService {
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;
    private final TermRepository termRepository;

    @Override
    public void lineupUser(Long userId, Long smallGroupId) {
        // user 검증

        // smallGroup 검증
        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        Term term = termRepository.findById(smallGroup.getTermId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작하지 않은 텀에 라인업할 수 없습니다.");
        }

        SmallGroupMember smallGroupMember = SmallGroupMember.builder()
                .termId(term.getId())
                .smallGroupId(smallGroupId)
                .userId(userId)
                .build();

        smallGroupMemberRepository.save(smallGroupMember);
    }

    @Override
    public Term findTermBySmallGroupId(Long smallGroupId) {
        SmallGroup smallGroup = smallGroupRepository.findById(smallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        return termRepository.findById(smallGroup.getTermId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));
    }
}
