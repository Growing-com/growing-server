package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamilyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLineOutNewFamilyRepository extends JpaRepository<LineOutNewFamily, Long>, LineOutNewFamilyRepository {
}
