package org.sarangchurch.growing.v2.feat.term.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.sarangchurch.growing.v2.feat.term.domain.term.Term;
import org.sarangchurch.growing.v2.feat.term.domain.term.TermRepository;
import org.sarangchurch.growing.v2.feat.term.infrastructure.stream.user.UserDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LineupManager {
    private final UserDownstream userDownstream;
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;
    private final TermRepository termRepository;


    @Transactional
    public void lineup(Long userId, Long smallGroupId) {
        userDownstream.userExistsById(userId);

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
}
