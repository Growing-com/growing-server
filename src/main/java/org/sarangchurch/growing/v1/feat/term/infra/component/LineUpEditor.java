package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LineUpEditor {
    private final SmallGroupMemberRepository smallGroupMemberRepository;
    private final SmallGroupRepository smallGroupRepository;
    private final TermFinder termFinder;

    @Transactional
    public void edit(Long smallGroupMemberId, Long targetSmallGroupId) {
        SmallGroupMember smallGroupMember = smallGroupMemberRepository.findById(smallGroupMemberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순원입니다."));

        SmallGroup targetSmallGroup = smallGroupRepository.findById(targetSmallGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        boolean isTermMatch = targetSmallGroup.getTermId().equals(smallGroupMember.getTermId());

        if (!isTermMatch) {
            throw new IllegalStateException("같은 텀의 순모임으로만 이동할 수 있습니다.");
        }

        smallGroupMember.updateSmallGroup(targetSmallGroup);
    }
}
