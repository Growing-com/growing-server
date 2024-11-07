package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupLeaderLineUpRepository extends JpaRepository<NewFamilyGroupLeaderLineUp, Long>, NewFamilyGroupLeaderLineUpRepository {
}
