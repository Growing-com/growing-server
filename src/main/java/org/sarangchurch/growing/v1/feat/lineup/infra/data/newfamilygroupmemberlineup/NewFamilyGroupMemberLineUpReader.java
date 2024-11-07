package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberLineUpReader {
    private final NewFamilyGroupMemberLineUpRepository newFamilyGroupMemberLineUpRepository;

    public boolean existsByMemberUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberLineUpRepository.existsByMemberUserIdAndTermId(userId, termId);
    }
}
