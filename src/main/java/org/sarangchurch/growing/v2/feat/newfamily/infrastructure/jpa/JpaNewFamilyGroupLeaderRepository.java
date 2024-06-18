package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupLeaderRepository extends JpaRepository<NewFamilyGroupLeader, Long>, NewFamilyGroupLeaderRepository {
}
