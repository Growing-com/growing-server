package org.sarangchurch.growing.v1.feat.attendance.infra.stream.term;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Attendance_Term_Downstream")
@RequiredArgsConstructor
public class TermDownstream {
    private final TermService termService;

    public boolean areValidStumpUserIdsByTermId(List<Long> userIds, Long termId) {
        return termService.areValidStumpUserIds(userIds, termId);
    }

    public Pair<Term, Cody> findTermAndCodyBySmallGroupId(Long smallGroupId) {
        return termService.findTermAndCodyBySmallGroupId(smallGroupId);
    }

    public Pair<Term, Cody> findTermAndCodyByNewFamilyGroupId(Long newFamilyGroupId) {
        return termService.findTermAndCodyByNewFamilyGroupId(newFamilyGroupId);
    }
}
