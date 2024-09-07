package org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily;

import java.util.List;
import java.util.Optional;

public interface LineoutNewFamilyRepository {
    LineoutNewFamily save(LineoutNewFamily lineoutNewFamily);
    Optional<LineoutNewFamily> findById(Long id);
    void deleteById(Long lineOutNewFamilyId);
    <S extends LineoutNewFamily> List<S> saveAll(Iterable<S> entities);
}
