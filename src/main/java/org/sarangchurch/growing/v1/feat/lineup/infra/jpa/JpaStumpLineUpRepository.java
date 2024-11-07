package org.sarangchurch.growing.v1.feat.lineup.infra.jpa;

import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStumpLineUpRepository extends JpaRepository<StumpLineUp, Long>, StumpLineUpRepository {
}
