package org.sarangchurch.growing.v1.feat.term.application.updatenewfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.NewFamilyGroupUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNewFamilyGroupService {
    private final NewFamilyGroupUpdater newFamilyGroupUpdater;

    public void update(Long newFamilyGroupId, UpdateNewFamilyGroupRequest request) {
        newFamilyGroupUpdater.update(newFamilyGroupId, request.getMemberUserIds());
    }
}
