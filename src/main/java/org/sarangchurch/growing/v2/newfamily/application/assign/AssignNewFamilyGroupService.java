package org.sarangchurch.growing.v2.newfamily.application.assign;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyNewFamilyGroupAssigner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignNewFamilyGroupService {
    private final NewFamilyNewFamilyGroupAssigner newFamilyNewFamilyGroupAssigner;

    public void assign(Long newFamilyId, Long newFamilyGroupId) {
        newFamilyNewFamilyGroupAssigner.assign(newFamilyId, newFamilyGroupId);
    }
}
