package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog;

import java.util.List;
import java.util.Optional;

public interface NewFamilyPromoteLogRepository {
    NewFamilyPromoteLog save(NewFamilyPromoteLog log);
    Optional<NewFamilyPromoteLog> findByNewFamilyId(Long newFamilyId);
    <S extends NewFamilyPromoteLog> List<S> saveAll(Iterable<S> entities);
    List<NewFamilyPromoteLog> findByIdIn(List<Long> ids);
    void deleteByIdIn(List<Long> ids);
}
