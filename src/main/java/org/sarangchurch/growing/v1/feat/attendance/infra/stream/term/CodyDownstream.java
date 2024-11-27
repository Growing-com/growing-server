package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.CodyService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_Cody_Downstream")
@RequiredArgsConstructor
public class CodyDownstream {
    private final CodyService codyService;

    public boolean areValidMemberUserIdsByCodyId(Long codyId, List<Long> userIds) {
        return codyService.areValidMemberUserIdsByCodyId(codyId, userIds);
    }
}
