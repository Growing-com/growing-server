package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import java.util.List;
import java.util.Optional;

public interface NewFamilyRepository {
    NewFamily save(NewFamily newFamily);
    Optional<NewFamily> findById(Long id);
    List<NewFamily> findByIdIn(List<Long> ids);
    void deleteByIdIn(List<Long> ids);
}
