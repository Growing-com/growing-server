package org.sarangchurch.growing.v1.feat.lineup.application.assigncody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner.CodyAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignCodyService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final CodyAssigner codyAssigner;

    public void assign(Long termId, List<Long> userIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        List<User> users = userDownstream.findActiveByIdInOrThrow(userIds);

        codyAssigner.assign(term, users);
    }
}
