package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import java.util.List;

public interface NewFamilyService {
    boolean existsByIds(List<Long> ids);

    boolean isNewFamilyByUserId(Long userId);
}
