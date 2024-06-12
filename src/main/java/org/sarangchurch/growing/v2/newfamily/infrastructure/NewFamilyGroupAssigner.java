package org.sarangchurch.growing.v2.newfamily.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupAssigner {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyGroupValidator newFamilyGroupValidator;

    @Transactional
    public void assign(Long newFamilyId, Long newFamilyGroupId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        newFamilyGroupValidator.validateAvailable(newFamilyGroupId);

        newFamily.assignNewFamilyGroup(newFamilyGroupId);
    }
}
