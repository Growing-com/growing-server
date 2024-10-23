package org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.infra.data.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SmallGroupRemover {
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final SmallGroupWriter smallGroupWriter;
    private final SmallGroupLeaderWriter smallGroupLeaderWriter;

    @Transactional
    public void remove(Long id) {
        long smallGroupMemberCount = smallGroupMemberFinder.countBySmallGroupId(id);

        if (smallGroupMemberCount > 0) {
            throw new IllegalStateException("현재 순모임에 존재하는 순원이 있어서 삭제할 수 없습니다.");
        }

        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(id);

        termFinder.findActiveByIdOrThrow(smallGroup.getTermId());

        smallGroupWriter.deleteById(smallGroup.getId());
        smallGroupLeaderWriter.deleteById(smallGroup.getSmallGroupLeaderId());
    }
}
