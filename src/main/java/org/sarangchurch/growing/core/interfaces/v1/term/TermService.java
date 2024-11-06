package org.sarangchurch.growing.core.interfaces.v1.term;

import com.mysema.commons.lang.Pair;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import java.util.List;

public interface TermService {
    Term findTerm(Long id);
    void emitByUserIds(List<Long> userIds);
    boolean areValidStumpUserIds(List<Long> userIds, Long termId);
    Pair<Term, Cody> findTermAndCodyBySmallGroupId(Long smallGroupId);
}
