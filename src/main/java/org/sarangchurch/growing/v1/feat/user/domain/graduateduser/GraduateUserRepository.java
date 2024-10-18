package org.sarangchurch.growing.v1.feat.user.domain.graduateduser;

import java.util.List;

public interface GraduateUserRepository {
    <S extends GraduatedUser> List<S> saveAll(Iterable<S> graduatedUsers);

    boolean existsByUserIdIn(List<Long> userIds);
}
