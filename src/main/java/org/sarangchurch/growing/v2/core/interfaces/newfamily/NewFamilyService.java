package org.sarangchurch.growing.v2.core.interfaces.newfamily;

import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;

import java.util.List;

public interface NewFamilyService {
    boolean existsByIds(List<Long> ids);

    List<NewFamily> findByIdIn(List<Long> ids);
}
