package org.sarangchurch.growing.v2.term.domain;

import java.util.Optional;

public interface SmallGroupRepository {
    Optional<SmallGroup> findById(Long id);
}
