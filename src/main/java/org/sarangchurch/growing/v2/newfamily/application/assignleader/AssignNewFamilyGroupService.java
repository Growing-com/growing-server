package org.sarangchurch.growing.v2.newfamily.application.assignleader;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyGroupValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignNewFamilyGroupService {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyGroupValidator newFamilyGroupValidator;

    public void assign(Long newFamilyId, Long newFamilyGroupId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        newFamilyGroupValidator.validateAvailable(newFamilyGroupId);

        newFamily.assignNewFamilyGroup(newFamilyGroupId);
    }

}
