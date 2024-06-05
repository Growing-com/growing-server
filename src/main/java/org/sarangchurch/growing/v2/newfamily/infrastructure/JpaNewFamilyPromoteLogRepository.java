package org.sarangchurch.growing.v2.newfamily.infrastructure;

import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLog;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewFamilyPromoteLogRepository extends JpaRepository<NewFamilyPromoteLog, Long>, NewFamilyPromoteLogRepository {
}
