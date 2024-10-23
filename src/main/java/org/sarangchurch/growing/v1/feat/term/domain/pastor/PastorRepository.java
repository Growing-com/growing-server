package org.sarangchurch.growing.v1.feat.term.domain.pastor;

import java.util.List;
import java.util.Optional;

public interface PastorRepository {
    boolean existsByUserIdAndTermId(Long userId, Long termId);

    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);

    Pastor save(Pastor pastor);

    Optional<Pastor> findById(Long id);

    void deleteById(Long id);

    Optional<Pastor> findSeniorByTermId(Long termId);
}
