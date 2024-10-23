package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.PastorRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPastorRepository extends JpaRepository<Pastor, Long>, PastorRepository {
}
