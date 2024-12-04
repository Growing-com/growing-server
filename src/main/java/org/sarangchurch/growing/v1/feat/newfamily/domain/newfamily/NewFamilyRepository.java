package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import java.util.List;
import java.util.Optional;

public interface NewFamilyRepository {
    Optional<NewFamily> findById(Long id);
    List<NewFamily> findByIdIn(List<Long> ids);
    Optional<NewFamily> findByUserId(Long userId);
    boolean existsByNewFamilyGroupId(Long newFamilyGroupId);
    NewFamily save(NewFamily newFamily);
    void deleteByIdIn(List<Long> ids);
    boolean existsBySmallGroupId(Long smallGroupId);
    long countTotalCurrent();
    List<NewFamily> findAllCurrent();
}
