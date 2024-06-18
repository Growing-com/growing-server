package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilygroupleader;

import java.util.Optional;

public interface NewFamilyGroupLeaderRepository {
    Optional<NewFamilyGroupLeader> findById(Long id);
}
