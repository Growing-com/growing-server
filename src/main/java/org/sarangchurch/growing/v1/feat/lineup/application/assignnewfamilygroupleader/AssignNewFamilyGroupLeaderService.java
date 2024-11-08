package org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamilygroupleader;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.NewFamilyGroupLeaderAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignNewFamilyGroupLeaderService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final NewFamilyGroupLeaderAssigner newFamilyGroupLeaderAssigner;

    public void assign(Long termId, Long codyUserId, List<Long> newFamilyGroupLeaderUserIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        User cody = userDownstream.findActiveByIdOrThrow(codyUserId);
        List<User> newFamilyGroupLeaders = userDownstream.findActiveByIdInOrThrow(newFamilyGroupLeaderUserIds);

        newFamilyGroupLeaderAssigner.assign(term, cody, newFamilyGroupLeaders);
    }
}
