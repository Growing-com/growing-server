package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyRepository extends JpaRepository<NewFamily, Long>, NewFamilyRepository {
}
