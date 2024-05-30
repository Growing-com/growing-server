package org.sarangchurch.growing.term.infra;

import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTermRepository extends JpaRepository<Term, Long>, TermRepository {
}
