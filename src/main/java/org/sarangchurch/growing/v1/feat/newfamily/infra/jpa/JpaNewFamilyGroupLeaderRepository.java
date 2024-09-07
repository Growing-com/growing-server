package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupLeaderRepository extends JpaRepository<NewFamilyGroupLeader, Long>, NewFamilyGroupLeaderRepository {
}
