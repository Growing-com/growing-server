package org.sarangchurch.growing.v2.feat.attendance.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.ForbiddenException;
import org.sarangchurch.growing.v2.feat.attendance.application.registernewfamilyattendance.RegisterNewFamilyAttendanceRequest;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyGroupLeaderService;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyService;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyAttendanceValidator {
    private final NewFamilyService newFamilyService;
    private final NewFamilyGroupService newFamilyGroupService;
    private final NewFamilyGroupLeaderService newFamilyGroupLeaderService;

    public void validate(Long userId, RegisterNewFamilyAttendanceRequest request) {
        List<Long> newFamilyIds = request.getAttendanceItems()
                .stream()
                .map(RegisterNewFamilyAttendanceRequest.NewFamilyAttendanceItems::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyService.findByIdIn(newFamilyIds);
        Set<Long> newFamilyGroupIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyGroupId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (newFamilyGroupIds.size() != 1) {
            throw new IllegalStateException("새가족반 하나씩 출석체크할 수 있습니다.");
        }

        Long newFamilyGroupId = newFamilies.get(0).getNewFamilyGroupId();
        NewFamilyGroup newFamilyGroup = newFamilyGroupService.findById(newFamilyGroupId);
        Long newFamilyGroupLeaderId = newFamilyGroup.getNewFamilyGroupLeaderId();
        NewFamilyGroupLeader newFamilyGroupLeader = newFamilyGroupLeaderService.findById(newFamilyGroupLeaderId);

        if (!newFamilyGroupLeader.getUserId().equals(userId)) {
            throw new ForbiddenException("출석체크할 수 있는 권한이 없습니다");
        }
    }
}
