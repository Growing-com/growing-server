package org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup;

import java.util.Optional;

public interface StumpLineUpRepository {
    Optional<StumpLineUp> findByTermId(Long termId);

    StumpLineUp save(StumpLineUp stumpLineUp);
}
