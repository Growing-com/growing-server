package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("V2JpaTermRepository")
public interface JpaTermRepository extends JpaRepository<Term, Long>, TermRepository {
    @Override
    @Query("SELECT t FROM Term t WHERE t.isActive = true")
    Optional<Term> findActive();
}
