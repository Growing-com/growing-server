package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSmallGroupMemberLineUpRepository extends JpaRepository<SmallGroupMemberLineUp, Long>, SmallGroupMemberLineUpRepository {
}
