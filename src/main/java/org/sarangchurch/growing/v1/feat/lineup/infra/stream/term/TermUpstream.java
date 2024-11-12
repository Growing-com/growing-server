package org.sarangchurch.growing.v1.feat.lineup.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("LineUp_Term_Upstream")
@RequiredArgsConstructor
public class TermUpstream {
    private final TermService termService;

    public void processLineUpsAndStartTerm(
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps,
            List<SmallGroupMemberLineUp> smallGroupMemberLineUps
    ) {
        termService.startTerm(stumpLineUp, smallGroupLeaderLineUps, smallGroupMemberLineUps);
    }
}
