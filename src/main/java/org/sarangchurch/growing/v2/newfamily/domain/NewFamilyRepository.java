package org.sarangchurch.growing.v2.newfamily.domain;

import java.util.Optional;

public interface NewFamilyRepository {
    NewFamily save(NewFamily newFamily);
    Optional<NewFamily> findById(Long id);
    void deleteById(Long id);
}
