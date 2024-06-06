package org.sarangchurch.growing.v2.newfamily.domain;

import java.util.Optional;

public interface NewFamilyGroupRepository {
    Optional<NewFamilyGroup> findById(Long id);
}
