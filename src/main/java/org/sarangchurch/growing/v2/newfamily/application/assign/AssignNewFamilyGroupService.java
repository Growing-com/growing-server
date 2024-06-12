package org.sarangchurch.growing.v2.newfamily.application.assign;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyGroupAssigner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignNewFamilyGroupService {
    private final NewFamilyGroupAssigner newFamilyGroupAssigner;

    public void assign(Long newFamilyId, Long newFamilyGroupId) {
        newFamilyGroupAssigner.assign(newFamilyId, newFamilyGroupId);
    }
}
