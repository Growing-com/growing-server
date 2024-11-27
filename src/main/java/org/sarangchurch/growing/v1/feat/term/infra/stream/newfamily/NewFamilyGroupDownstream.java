package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupDownstream {
    private final NewFamilyGroupService newFamilyGroupService;

    public long countByCodyId(Long codyId) {
        return newFamilyGroupService.countByCodyId(codyId);
    }

    public NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId) {
        return newFamilyGroupService.findByIdOrThrow(newFamilyGroupId);
    }

    public boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId) {
        return newFamilyGroupService.existsByLeaderUserIdAndTermId(leaderUserId, termId);
    }

    public boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId) {
        return newFamilyGroupService.existsByLeaderUserIdInAndTermId(leaderUserIds, termId);
    }
}
