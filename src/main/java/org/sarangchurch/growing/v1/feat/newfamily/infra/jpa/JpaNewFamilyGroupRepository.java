package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupRepository extends JpaRepository<NewFamilyGroup, Long>, NewFamilyGroupRepository {
}
