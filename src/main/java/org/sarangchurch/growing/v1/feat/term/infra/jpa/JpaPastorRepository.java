package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.PastorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPastorRepository extends JpaRepository<Pastor, Long>, PastorRepository {
    @Override
    @Query("SELECT p FROM Pastor p WHERE p.termId = :termId AND p.isSenior = true")
    Optional<Pastor> findSeniorByTermId(Long termId);
}
