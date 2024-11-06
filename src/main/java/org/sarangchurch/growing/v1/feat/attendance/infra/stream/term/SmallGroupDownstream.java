package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_SmallGroup_Downstream")
@RequiredArgsConstructor
public class SmallGroupDownstream {
    private final SmallGroupService smallGroupService;

    public boolean areValidUserIdsBySmallGroupId(List<Long> userIds, Long smallGroupId) {
        return smallGroupService.areValidUserIdsBySmallGroupId(userIds, smallGroupId);
    }
}
