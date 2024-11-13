package org.sarangchurch.growing.v1.feat.lineup.application.assignjuniorpastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner.JuniorPastorAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignJuniorPastorService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final JuniorPastorAssigner juniorPastorAssigner;

    public void assign(Long termId, List<Long> userIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        List<User> users = userDownstream.findActiveByIdInOrThrow(userIds);

        juniorPastorAssigner.assign(term, users);
    }
}
