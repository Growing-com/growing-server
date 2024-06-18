package org.sarangchurch.growing.v2.feat.newfamily.application.rollback;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyNewFamilyGroupAssigner;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyRollbackManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RollbackService {
    private final NewFamilyRollbackManager newFamilyRollbackManager;
    private final NewFamilyNewFamilyGroupAssigner newFamilyGroupAssigner;

    @Transactional
    public void rollback(Long linedOutNewFamilyId, RollbackRequest request) {
        if (request.getNewFamilyGroupId() == null) {
            newFamilyRollbackManager.rollback(linedOutNewFamilyId);

            return;
        }

        NewFamily newFamily = newFamilyRollbackManager.rollback(linedOutNewFamilyId);
        newFamilyGroupAssigner.assign(newFamily.getId(), request.getNewFamilyGroupId());
    }
}
