package org.sarangchurch.growing.v1.feat.term.domain.cody;

import java.util.List;
import java.util.Optional;

public interface CodyRepository {
    Optional<Cody> findById(Long id);
    boolean existsByUserIdAndTermId(Long userId, Long termId);
    boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId);
    Cody save(Cody cody);
    void deleteById(Long id);
    Optional<Cody> findByUserIdAndTermId(Long userId, Long termId);
    List<Cody> findByTermId(Long termId);
}
