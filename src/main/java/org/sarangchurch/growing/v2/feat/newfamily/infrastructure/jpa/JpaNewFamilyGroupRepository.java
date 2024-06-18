package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupRepository extends JpaRepository<NewFamilyGroup, Long>, NewFamilyGroupRepository {
}
