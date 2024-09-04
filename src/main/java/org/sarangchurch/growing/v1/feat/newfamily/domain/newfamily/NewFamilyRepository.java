package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;

import java.util.List;
import java.util.Optional;

public interface NewFamilyRepository {
    NewFamily save(NewFamily newFamily);
    Optional<NewFamily> findById(Long id);
    void deleteById(Long id);
    List<NewFamily> findByIdIn(List<Long> ids);
}
