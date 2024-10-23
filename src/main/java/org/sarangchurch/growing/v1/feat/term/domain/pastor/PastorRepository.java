package org.sarangchurch.growing.v1.feat.term.domain.pastor;

import java.util.List;

public interface PastorRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);
}
