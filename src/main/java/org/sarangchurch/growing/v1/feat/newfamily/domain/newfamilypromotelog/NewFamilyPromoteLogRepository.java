package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog;

import java.util.List;

public interface NewFamilyPromoteLogRepository {
    NewFamilyPromoteLog save(NewFamilyPromoteLog log);
    <S extends NewFamilyPromoteLog> List<S> saveAll(Iterable<S> entities);
    List<NewFamilyPromoteLog> findByIdIn(List<Long> ids);
    void deleteByIdIn(List<Long> ids);
}
