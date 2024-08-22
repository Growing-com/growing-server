package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog;

import java.util.Optional;

public interface NewFamilyPromoteLogRepository {
    NewFamilyPromoteLog save(NewFamilyPromoteLog log);
    Optional<NewFamilyPromoteLog> findByNewFamilyId(Long newFamilyId);
}
