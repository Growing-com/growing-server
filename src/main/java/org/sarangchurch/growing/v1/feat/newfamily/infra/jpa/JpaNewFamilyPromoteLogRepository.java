package org.sarangchurch.growing.v1.feat.newfamily.infra.jpa;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyPromoteLogRepository extends JpaRepository<NewFamilyPromoteLog, Long>, NewFamilyPromoteLogRepository {
}
