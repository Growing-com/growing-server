package org.sarangchurch.growing.core.interfaces.v1.term;

import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

public interface TermService {
    Term findTerm(Long id);
}
