package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupFinder {
    private final NewFamilyGroupRepository newFamilyGroupRepository;

    public long countByCodyId(Long codyId) {
        return newFamilyGroupRepository.countByCodyId(codyId);
    }

    public NewFamilyGroup findByIdOrThrow(Long id) {
        return newFamilyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반입니다."));
    }

    public List<NewFamilyGroup> findByTermId(Long termId) {
        return newFamilyGroupRepository.findByTermId(termId);
    }

    public boolean existsByCodyIdAndLeaderUserId(Long codyId, Long leaderUserId) {
        return newFamilyGroupRepository.existsByCodyIdAndLeaderUserId(codyId, leaderUserId);
    }

    public boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId) {
        return newFamilyGroupRepository.existsByLeaderUserIdAndTermId(leaderUserId, termId);
    }

    public boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId) {
        return newFamilyGroupRepository.existsByLeaderUserIdInAndTermId(leaderUserIds, termId);
    }
}
