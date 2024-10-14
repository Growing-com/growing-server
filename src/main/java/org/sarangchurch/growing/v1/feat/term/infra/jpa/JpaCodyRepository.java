package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCodyRepository extends JpaRepository<Cody, Long>, CodyRepository {
}
