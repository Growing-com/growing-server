package org.sarangchurch.growing.term.domain.term;

import java.util.Optional;

public interface TermRepository {
    Term save(Term term);
    boolean existsByName(String name);
    Optional<Term> findById(Long termId);
    boolean existsByStatus(TermStatus status);
    void deleteById(Long termId);
}
