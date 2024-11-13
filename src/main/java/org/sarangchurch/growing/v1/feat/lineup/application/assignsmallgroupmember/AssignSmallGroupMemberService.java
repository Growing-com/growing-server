package org.sarangchurch.growing.v1.feat.lineup.application.assignsmallgroupmember;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner.SmallGroupMemberAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignSmallGroupMemberService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final SmallGroupMemberAssigner smallGroupMemberAssigner;

    public void assign(Long termId, Long leaderUserId, List<Long> memberUserIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        User leader = userDownstream.findActiveByIdOrThrow(leaderUserId);
        List<User> members = userDownstream.findActiveByIdInOrThrow(memberUserIds);

        smallGroupMemberAssigner.assign(term, leader, members);
    }
}
