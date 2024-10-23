package org.sarangchurch.growing.v1.feat.term.application.smallgroup.updatesmallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup.SmallGroupUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSmallGroupService {
    private final SmallGroupUpdater smallGroupUpdater;

    public void update(Long smallGroupId, UpdateSmallGroupRequest request) {
        smallGroupUpdater.update(smallGroupId, request.getMemberUserIds());
    }
}
