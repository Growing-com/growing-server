package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupLeaderLineUpRepository extends JpaRepository<SmallGroupLeaderLineUp, Long>, SmallGroupLeaderLineUpRepository {
}
