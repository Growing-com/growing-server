package org.sarangchurch.growing.v1.core.interfaces.term;

import org.sarangchurch.growing.v2.feat.term.domain.term.Term;

public interface V1TermService {
    void lineupUser(Long userId, Long smallGroupId);

    Term findTermBySmallGroupId(Long smallGroupId);

    Term findTerm(Long id);
}
