package org.sarangchurch.growing.v1.feat.term.domain.term;

import java.util.Optional;

public interface TermRepository {
    Optional<Term> findById(Long id);

    Optional<Term> findActive();

    Optional<Term> findByStatus(TermStatus termStatus);

    Term save(Term term);
}
