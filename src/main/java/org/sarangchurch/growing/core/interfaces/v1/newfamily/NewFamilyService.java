package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import java.util.List;

public interface NewFamilyService {
    boolean existsAllByIds(List<Long> ids);

    boolean isNewFamilyByUserId(Long userId);

    boolean containsNewFamilyByUserIds(List<Long> userIds);

    boolean existsByNewFamilyGroupId(Long newFamilyGroupId);
}
