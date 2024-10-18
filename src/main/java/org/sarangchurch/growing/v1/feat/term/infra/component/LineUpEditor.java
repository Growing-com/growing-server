package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LineUpEditor {
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final TermFinder termFinder;
    private final SmallGroupFinder smallGroupFinder;

    @Transactional
    public void edit(Long smallGroupMemberId, Long targetSmallGroupId) {
        SmallGroupMember smallGroupMember = smallGroupMemberFinder.findByIdOrThrow(smallGroupMemberId);
        Term term = termFinder.findActiveByIdOrThrow(smallGroupMember.getTermId());
        SmallGroup targetSmallGroup = smallGroupFinder.findByIdOrThrow(targetSmallGroupId);

        boolean isTermMatch = targetSmallGroup.isSameTerm(term);

        if (!isTermMatch) {
            throw new IllegalStateException("같은 텀의 순모임으로만 이동할 수 있습니다.");
        }

        smallGroupMember.updateSmallGroup(targetSmallGroup);
    }
}
