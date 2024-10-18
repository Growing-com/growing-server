package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberDownstream {
    private final NewFamilyGroupMemberService newFamilyGroupMemberService;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberService.existsByUserIdAndTermId(userId, termId);
    }
}
