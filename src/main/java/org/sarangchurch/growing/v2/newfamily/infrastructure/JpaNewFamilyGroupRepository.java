package org.sarangchurch.growing.v2.newfamily.infrastructure;

import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroup;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyGroupRepository extends JpaRepository<NewFamilyGroup, Long>, NewFamilyGroupRepository {
}
