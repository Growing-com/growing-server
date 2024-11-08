package org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupleader;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.SmallGroupLeaderAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignSmallGroupLeaderService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final SmallGroupLeaderAssigner smallGroupLeaderAssigner;

    public void assign(Long termId, Long codyUserId, List<Long> smallGroupLeaderUserIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        User cody = userDownstream.findActiveByIdOrThrow(codyUserId);
        List<User> smallGroupLeaders = userDownstream.findActiveByIdInOrThrow(smallGroupLeaderUserIds);

        smallGroupLeaderAssigner.assign(term, cody, smallGroupLeaders);
    }
}
