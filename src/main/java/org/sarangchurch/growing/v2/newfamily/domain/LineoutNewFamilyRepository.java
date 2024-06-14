package org.sarangchurch.growing.v2.newfamily.domain;

import java.util.Optional;

public interface LineoutNewFamilyRepository {
    LineoutNewFamily save(LineoutNewFamily lineoutNewFamily);
    Optional<LineoutNewFamily> findById(Long id);
    void deleteById(Long lineOutNewFamilyId);
}
