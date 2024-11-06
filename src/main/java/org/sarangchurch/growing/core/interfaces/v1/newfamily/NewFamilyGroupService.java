package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;

import java.util.List;

public interface NewFamilyGroupService {
    long countByCodyId(Long codyId);

    NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId);

    boolean areValidUserIdsByNewFamilyGroupId(List<Long> userIds, Long newFamilyGroupId);
}
