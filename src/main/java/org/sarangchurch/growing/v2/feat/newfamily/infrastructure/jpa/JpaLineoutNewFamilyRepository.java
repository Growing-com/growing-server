package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.jpa;

import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamilyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLineoutNewFamilyRepository extends JpaRepository<LineoutNewFamily, Long>, LineoutNewFamilyRepository {
}
