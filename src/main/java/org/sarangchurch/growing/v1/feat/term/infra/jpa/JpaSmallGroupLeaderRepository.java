package org.sarangchurch.growing.v1.feat.term.infra.jpa;

import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupLeaderRepository extends JpaRepository<SmallGroupLeader, Long>, SmallGroupLeaderRepository {
}
