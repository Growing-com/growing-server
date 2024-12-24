package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_Term_Downstream")
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public boolean areValidCodyUserIdsByTermId(List<Long> userIds, Long termId) {
        return termService.areValidCodyUserIdsByTermId(userIds, termId);
    }

    public boolean areValidMemberUserIdsByCodyId(List<Long> userIds, Long codyId) {
        return termService.areValidMemberUserIdsByCodyId(userIds, codyId);
    }

    public Term findByIdOrThrow(Long termId) {
        return termService.findTerm(termId);
    }

    public boolean containsCodyByTermIdAndCodyId(Long termId, Long codyId) {
        return termService.containsCodyByTermIdAndCodyId(termId, codyId);
    }
}
