package org.sarangchurch.growing.v1.feat.term.application.newfamilygroup.deletenewfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.newfamilygroup.NewFamilyGroupRemover;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNewFamilyGroupService {
    private final NewFamilyGroupRemover newFamilyGroupRemover;

    public void delete(Long newFamilyGroupId) {
        newFamilyGroupRemover.remove(newFamilyGroupId);
    }
}
