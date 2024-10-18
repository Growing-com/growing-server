package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberFinder {
    private final NewFamilyGroupMemberRepository newFamilyGroupMemberRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberRepository.existsByUserIdAndTermId(userId, termId);
    }
}
