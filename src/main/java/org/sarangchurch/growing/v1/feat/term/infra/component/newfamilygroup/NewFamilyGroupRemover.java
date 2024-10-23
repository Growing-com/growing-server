package org.sarangchurch.growing.v1.feat.term.infra.component.newfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyGroupWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupLeaderUpstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupMemberDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupRemover {
    private final NewFamilyGroupMemberDownstream newFamilyGroupMemberDownstream;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final NewFamilyDownstream newFamilyDownstream;
    private final TermFinder termFinder;
    private final NewFamilyGroupWriter newFamilyGroupWriter;
    private final NewFamilyGroupLeaderUpstream newFamilyGroupLeaderUpstream;

    @Transactional
    public void remove(Long newFamilyGroupId) {
        long groupMemberCount = newFamilyGroupMemberDownstream.countByNewFamilyGroupId(newFamilyGroupId);

        if (groupMemberCount > 0) {
            throw new IllegalStateException("현재 새가족반에 배정된 순원이 있어서 삭제할 수 없습니다.");
        }

        boolean existsNewFamily = newFamilyDownstream.existsByNewFamilyGroupId(newFamilyGroupId);

        if (existsNewFamily) {
            throw new IllegalStateException("새가족반에 배정된 혹은 배정되었던 새가족이 있어서 삭제할 수 없습니다.");
        }

        NewFamilyGroup newFamilyGroup = newFamilyGroupDownstream.findByIdOrThrow(newFamilyGroupId);

        termFinder.findActiveByIdOrThrow(newFamilyGroup.getTermId());

        newFamilyGroupWriter.deleteById(newFamilyGroup.getId());
        newFamilyGroupLeaderUpstream.deleteById(newFamilyGroup.getNewFamilyGroupLeaderId());
    }
}
