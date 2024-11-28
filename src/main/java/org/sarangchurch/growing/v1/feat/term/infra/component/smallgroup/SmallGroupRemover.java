package org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamily.NewFamilyDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SmallGroupRemover {
    private final NewFamilyDownstream newFamilyDownstream;
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final SmallGroupWriter smallGroupWriter;

    @Transactional
    public void remove(Long id) {
        boolean newFamilyExists = newFamilyDownstream.existsBySmallGroupId(id);

        if (newFamilyExists) {
            throw new IllegalStateException("해당 순모임으로 등반했던 새가족이 있어서 삭제할 수 없습니다.");
        }

        long smallGroupMemberCount = smallGroupMemberFinder.countBySmallGroupId(id);

        if (smallGroupMemberCount > 0) {
            throw new IllegalStateException("현재 순모임에 존재하는 순원이 있어서 삭제할 수 없습니다.");
        }

        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(id);

        termFinder.findActiveByIdOrThrow(smallGroup.getTermId());

        smallGroupWriter.deleteById(smallGroup.getId());
    }
}
