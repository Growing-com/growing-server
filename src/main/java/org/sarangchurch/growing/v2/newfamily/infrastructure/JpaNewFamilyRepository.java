package org.sarangchurch.growing.v2.newfamily.infrastructure;

import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyRepository extends JpaRepository<NewFamily, Long>, NewFamilyRepository {
}
