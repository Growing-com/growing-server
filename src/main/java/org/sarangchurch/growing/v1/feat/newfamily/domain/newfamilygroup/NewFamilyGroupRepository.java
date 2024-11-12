package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup;

import java.util.List;
import java.util.Optional;

public interface NewFamilyGroupRepository {
    Optional<NewFamilyGroup> findById(Long id);

    long countByCodyId(Long codyId);

    boolean existsByCodyIdAndNewFamilyGroupLeaderId(Long codyId, Long newFamilyGroupLeaderId);

    NewFamilyGroup save(NewFamilyGroup newFamilyGroup);

    void deleteById(Long newFamilyGroupId);

    List<NewFamilyGroup> findByTermId(Long termId);
}
