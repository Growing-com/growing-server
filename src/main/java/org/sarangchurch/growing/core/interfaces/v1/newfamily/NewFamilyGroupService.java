package org.sarangchurch.growing.core.interfaces.v1.newfamily;

import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;

import java.util.List;

public interface NewFamilyGroupService {
    long countByCodyId(Long codyId);

    NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId);

    void processLineUps(
            List<NewFamilyGroupLeaderLineUp> leaderLineUps,
            List<NewFamilyGroupMemberLineUp> memberLineUps
    );

    boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId);

    boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId);

    List<NewFamilyGroup> findByCodyId(Long codyId);
}
