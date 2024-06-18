package org.sarangchurch.growing.v2.newfamily.infrastructure.jpa;

import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupLeader;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupLeaderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupLeaderRepository extends JpaRepository<NewFamilyGroupLeader, Long>, NewFamilyGroupLeaderRepository {
}
