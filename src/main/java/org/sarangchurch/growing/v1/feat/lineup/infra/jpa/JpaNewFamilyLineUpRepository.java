package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyLineUpRepository extends JpaRepository<NewFamilyLineUp, Long>, NewFamilyLineUpRepository {
}
