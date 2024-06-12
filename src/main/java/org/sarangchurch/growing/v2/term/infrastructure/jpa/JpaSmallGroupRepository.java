package org.sarangchurch.growing.v2.term.infrastructure.jpa;

import org.sarangchurch.growing.v2.term.domain.SmallGroup;
import org.sarangchurch.growing.v2.term.domain.SmallGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupRepository extends JpaRepository<SmallGroup, Long>, SmallGroupRepository {
}
