package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog;

import java.util.List;
import java.util.Optional;

public interface NewFamilyPromoteLogRepository {
    List<NewFamilyPromoteLog> findByIdIn(List<Long> ids);
    Optional<NewFamilyPromoteLog> findById(Long id);
    NewFamilyPromoteLog save(NewFamilyPromoteLog log);
    <S extends NewFamilyPromoteLog> List<S> saveAll(Iterable<S> entities);
    void deleteByIdIn(List<Long> ids);
    boolean existsBySmallGroupId(Long smallGroupId);
}
