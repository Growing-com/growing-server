package org.sarangchurch.growing.v2.feat.term.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v2.feat.term.domain.smallgroup.SmallGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupRepository extends JpaRepository<SmallGroup, Long>, SmallGroupRepository {
}
