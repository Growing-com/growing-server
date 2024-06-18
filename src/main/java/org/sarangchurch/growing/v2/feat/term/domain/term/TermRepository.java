package org.sarangchurch.growing.v2.feat.term.domain.term;

import java.util.Optional;

public interface TermRepository {
    Optional<Term> findById(Long id);
}
