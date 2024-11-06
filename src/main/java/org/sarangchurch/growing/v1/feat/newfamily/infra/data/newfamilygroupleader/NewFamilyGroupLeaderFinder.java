package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderFinder {
    private final NewFamilyGroupLeaderRepository newFamilyGroupLeaderRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderRepository.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupLeaderRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public NewFamilyGroupLeader findByIdOrThrow(Long id) {
        return newFamilyGroupLeaderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순장입니다."));
    }
}
