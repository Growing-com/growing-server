package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_Term_Downstream")
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public boolean areValidStumpUserIdsByTermId(List<Long> userIds, Long termId) {
        return termService.areValidStumpUserIds(userIds, termId);
    }
}
