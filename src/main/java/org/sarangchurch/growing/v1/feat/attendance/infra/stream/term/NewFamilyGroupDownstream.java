package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_NewFamilyGroup_Downstream")
@RequiredArgsConstructor
public class NewFamilyGroupDownstream {
    private final NewFamilyGroupService newFamilyGroupService;

    public boolean areValidUserIdsByNewFamilyGroupId(List<Long> userIds, Long newFamilyGroupId) {
        return newFamilyGroupService.areValidUserIdsByNewFamilyGroupId(userIds, newFamilyGroupId);
    }
}
