package org.sarangchurch.growing.v2.newfamily.domain;

import java.util.Optional;

public interface NewFamilyGroupLeaderRepository {
    Optional<NewFamilyGroupLeader> findById(Long id);
}
