package org.sarangchurch.growing.v1.core.interfaces.term;

import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

public interface TermService {
    Term findTerm(Long id);
}
