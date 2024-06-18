package org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily;

import java.util.Optional;

public interface LineoutNewFamilyRepository {
    LineoutNewFamily save(LineoutNewFamily lineoutNewFamily);
    Optional<LineoutNewFamily> findById(Long id);
    void deleteById(Long lineOutNewFamilyId);
}
