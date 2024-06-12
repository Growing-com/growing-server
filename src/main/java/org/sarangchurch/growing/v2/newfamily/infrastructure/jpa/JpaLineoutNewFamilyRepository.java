package org.sarangchurch.growing.v2.newfamily.infrastructure.jpa;

import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamilyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLineoutNewFamilyRepository extends JpaRepository<LineoutNewFamily, Long>, LineoutNewFamilyRepository {
}
