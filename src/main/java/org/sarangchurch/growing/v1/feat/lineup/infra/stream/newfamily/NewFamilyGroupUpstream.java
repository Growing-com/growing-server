package org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("LineUp_NewFamilyGroup_Upstream")
@RequiredArgsConstructor
public class NewFamilyGroupUpstream {
    private final NewFamilyGroupService newFamilyGroupService;

    public void processLineUps(
            List<NewFamilyGroupLeaderLineUp> leaderLineUps,
            List<NewFamilyGroupMemberLineUp> memberLineUps
    ) {
        newFamilyGroupService.processLineUps(leaderLineUps, memberLineUps);
    }
}
