package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroup;

import java.util.Optional;

public interface NewFamilyGroupRepository {
    Optional<NewFamilyGroup> findById(Long id);
}
