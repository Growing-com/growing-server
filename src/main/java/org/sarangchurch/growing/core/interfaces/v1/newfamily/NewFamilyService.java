package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;

import java.util.List;

public interface NewFamilyService {
    boolean existsAllByIds(List<Long> ids);

    boolean isNewFamilyByUserId(Long userId);

    boolean containsNewFamilyByUserIds(List<Long> userIds);

    boolean existsByNewFamilyGroupId(Long newFamilyGroupId);

    List<NewFamily> findByIdInOrThrow(List<Long> newFamilyIds);

    void processLineUps(List<NewFamilyLineUp> newFamilyLineUps);
}
