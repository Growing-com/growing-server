package org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily;

import java.util.List;
import java.util.Optional;

public interface LineOutNewFamilyRepository {
    LineOutNewFamily save(LineOutNewFamily lineoutNewFamily);
    Optional<LineOutNewFamily> findById(Long id);
    void deleteById(Long lineOutNewFamilyId);
    <S extends LineOutNewFamily> List<S> saveAll(Iterable<S> entities);
}
