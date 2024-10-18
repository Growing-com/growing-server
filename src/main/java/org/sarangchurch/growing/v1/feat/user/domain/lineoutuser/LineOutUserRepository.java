package org.sarangchurch.growing.v1.feat.user.domain.lineoutuser;

import java.util.List;
import java.util.Optional;

public interface LineOutUserRepository {
    boolean existsByUserIdIn(List<Long> userIds);

    <S extends LineOutUser> List<S> saveAll(Iterable<S> lineOutUsers);

    Optional<LineOutUser> findById(Long id);

    void deleteById(Long id);
}
