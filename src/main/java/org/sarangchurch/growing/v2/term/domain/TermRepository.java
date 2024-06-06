package org.sarangchurch.growing.v2.term.domain;

import java.util.Optional;

public interface TermRepository {
    Optional<Term> findById(Long id);
}
