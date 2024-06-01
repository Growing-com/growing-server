package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignLeaderService {
    private final NewFamilyRepository newFamilyRepository;

    public void assign(Long newFamilyId, Long newFamilyLeaderId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        validateNewFamilyLeader(newFamilyLeaderId);

        newFamily.setNewFamilyLeader(newFamilyLeaderId);
    }

    private void validateNewFamilyLeader(Long newFamilyLeaderId) {
        // TODO: 새가족반 순장 id 검증
    }
}
