package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;

import java.util.List;

public interface NewFamilyService {
    boolean areCurrentNewFamiliesByIds(List<Long> newFamilyIds);

    boolean isNewFamilyByUserId(Long userId);

    boolean containsNewFamilyByUserIds(List<Long> userIds);

    boolean existsByNewFamilyGroupId(Long newFamilyGroupId);

    List<NewFamily> findByIdInOrThrow(List<Long> newFamilyIds);

    void processLineUps(List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps, List<NewFamilyLineUp> newFamilyLineUps);

    boolean existsBySmallGroupId(Long smallGroupId);
}
