package org.sarangchurch.growing.v2.feat.newfamily.application.assign;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyNewFamilyGroupAssigner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignNewFamilyGroupService {
    private final NewFamilyNewFamilyGroupAssigner newFamilyNewFamilyGroupAssigner;

    public void assign(Long newFamilyId, Long newFamilyGroupId) {
        newFamilyNewFamilyGroupAssigner.assign(newFamilyId, newFamilyGroupId);
    }
}
