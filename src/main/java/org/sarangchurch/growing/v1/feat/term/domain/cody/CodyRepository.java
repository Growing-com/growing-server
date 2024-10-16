package org.sarangchurch.growing.v1.feat.term.domain.cody;

import java.util.Optional;

public interface CodyRepository {
    Optional<Cody> findById(Long id);
    boolean existsByUserIdAndTermId(Long userId, Long termId);
    Cody save(Cody cody);
    void deleteById(Long id);
}
