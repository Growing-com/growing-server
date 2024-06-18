package org.sarangchurch.growing.v2.feat.term.domain.smallgroup;

import java.util.Optional;

public interface SmallGroupRepository {
    Optional<SmallGroup> findById(Long id);
}
