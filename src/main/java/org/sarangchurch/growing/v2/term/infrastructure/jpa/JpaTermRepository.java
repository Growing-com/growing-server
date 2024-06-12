package org.sarangchurch.growing.v2.term.infrastructure.jpa;

import org.sarangchurch.growing.v2.term.domain.Term;
import org.sarangchurch.growing.v2.term.domain.TermRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("V2JpaTermRepository")
public interface JpaTermRepository extends JpaRepository<Term, Long>, TermRepository {
}
