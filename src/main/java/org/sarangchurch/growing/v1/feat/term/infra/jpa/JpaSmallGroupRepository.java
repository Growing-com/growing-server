package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupRepository extends JpaRepository<SmallGroup, Long>, SmallGroupRepository {
}
