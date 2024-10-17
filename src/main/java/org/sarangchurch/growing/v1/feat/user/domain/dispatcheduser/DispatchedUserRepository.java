package org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser;

import java.util.List;
import java.util.Optional;

public interface DispatchedUserRepository {
    boolean existsByUserIdIn(List<Long> userIds);

    <S extends DispatchedUser> List<S> saveAll(Iterable<S> entities);

    void deleteById(Long dispatchUserId);

    Optional<DispatchedUser> findById(Long dispatchUserId);
}
