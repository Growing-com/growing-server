package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupLeaderService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderDownstream {
    private final NewFamilyGroupLeaderService newFamilyGroupLeaderService;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderService.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupLeaderService.existsByUserIdInAndTermId(userIds, termId);
    }
}
